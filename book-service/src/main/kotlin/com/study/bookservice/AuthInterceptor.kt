package com.study.bookservice

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthInterceptor @Autowired constructor(
    private val restTemplate: RestTemplate,
    environment: Environment
) : HandlerInterceptor {
    private val logger = LoggerFactory.getLogger(this::class.java)

    private val name = environment["spring.application.name"] ?: throw IllegalArgumentException()
    private val authUrl = environment["auth.validate.url"] ?: throw IllegalArgumentException()

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        logger.info("Pre handle request")

        val token = request.getHeader("Authorization")

        val headers = HttpHeaders(). apply { add("Authorization", token)  }
        val entity = HttpEntity(name, headers)
        val authResponse = restTemplate.postForEntity(authUrl, entity, String::class.java)

        if (authResponse.statusCode == HttpStatus.OK) return true
        logger.warn("Token is not valid")
        response.status = HttpStatus.UNAUTHORIZED.value()
        return false
    }
}