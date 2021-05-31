package com.study.gateway.conroller

import com.study.gateway.dto.ErrorResponse
import com.study.gateway.extensions.addTracing
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.cloud.sleuth.Tracer
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

sealed class RequestResult {
    data class Success<out T : Any>(val data: T) : RequestResult()
    data class Error(val statusCode: Int) : RequestResult()
    object InternalError : RequestResult()
}

abstract class BaseController(
    private val client: HttpClient,
    private val tracer : Tracer,
    environment: Environment) {

    private val serviceName : String = environment["spring.application.name"] ?: throw IllegalArgumentException()
    private val authUrl : String = environment["auth.service.url"] ?: throw IllegalArgumentException()
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun sendRequest(
        uriString: String,
        method: HttpMethod,
        body: Any? = null
    ): ResponseEntity<Any> = runBlocking {
        val token = getToken()
        if (token == null) {
            val errorResponse = ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR)
            return@runBlocking ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
        }

        val result = try {
            val response = client.request<HttpResponse>(uriString) {
                this.method = method
                if (body != null) {
                    this.body = body
                    contentType(ContentType.Application.Json)
                }
                addTracing(tracer.currentSpan()?.context())
                header("Authorization", token)
            }
            if (response.status == HttpStatusCode.OK) {
                RequestResult.Success(response.readText())
            } else {
                RequestResult.Error(response.status.value)
            }
        } catch (e: Exception) {
            logger.error("An error has occurred: ${e.message}", e)
            RequestResult.InternalError
        }
        return@runBlocking processResult(result)
    }

    private fun processResult(result: RequestResult): ResponseEntity<Any> {
        return when (result) {
            is RequestResult.Success<*> -> {
                ResponseEntity.ok().body(result.data)
            }
            is RequestResult.Error -> {
                val statusCode = result.statusCode
                val errorResponse = ErrorResponse(HttpStatus.valueOf(statusCode))
                ResponseEntity.status(statusCode).body(errorResponse)
            }
            is RequestResult.InternalError -> {
                val errorResponse = ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR)
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
            }
        }
    }

    private suspend fun getToken(): String? {
        logger.info("Getting access token")
        return try {
            val response = client.post<HttpResponse>(authUrl) {
                addTracing(tracer.currentSpan()?.context())
                body = serviceName
            }
            if (response.status == HttpStatusCode.OK) response.readText()
            else null
        } catch (e: Exception) {
            logger.error("An error has occurred: ${e.message}", e)
            null
        }
    }
}