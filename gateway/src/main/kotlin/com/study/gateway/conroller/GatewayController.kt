package com.study.gateway.conroller

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.client.*
import io.ktor.http.*
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.util.UriComponentsBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.sleuth.Tracer
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/v1/books")
class GatewayController @Autowired constructor(
    client: HttpClient,
    tracer : Tracer,
    environment: Environment
) : BaseController(client, tracer, environment) {

    private val bookServiceUri = environment["book.service.uri"] ?: throw IllegalArgumentException()
    private val favoritesServiceUri = environment["favorites.service.uri"] ?: throw IllegalArgumentException()

    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getBooks(
        @RequestParam(value = "offset", defaultValue = "0")
        offset: Int,
        @RequestParam(value = "limit", defaultValue = "25")
        limit: Int
    ): ResponseEntity<Any> {
        logger.info("Retrieving $limit books starting with offset = $offset")

        val uri = UriComponentsBuilder
            .fromUriString(bookServiceUri)
            .path("/books")
            .queryParam("offset", offset)
            .queryParam("limit", limit)
            .toUriString()
        return sendRequest(uri, HttpMethod.Get)
    }

    @GetMapping("/{bookId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getBookDetails(
        @PathVariable bookId: Int
    ): ResponseEntity<Any> {
        logger.info("Retrieving the book with id = $bookId")

        val uri = UriComponentsBuilder
            .fromUriString(bookServiceUri)
            .path("/books/${bookId}")
            .toUriString()
        return sendRequest(uri, HttpMethod.Get)
    }

    @GetMapping("/favorites", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getFavorites(): ResponseEntity<Any> {
        logger.info("Retrieving favorite books")

        val uri = UriComponentsBuilder
            .fromUriString(favoritesServiceUri)
            .path("/favorites")
            .toUriString()
        return sendRequest(uri, HttpMethod.Get)
    }

    @PostMapping("/{bookId}/like", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun addToFavorite(
        @PathVariable
        bookId: Int
    ): ResponseEntity<Any> {
        logger.info("Adding the book with id = $bookId to favorites")

        val uri = UriComponentsBuilder
            .fromUriString(favoritesServiceUri)
            .path("/favorites")
            .toUriString()
        val body = ObjectMapper().createObjectNode().apply {
            put("bookId", bookId)
        }
        return sendRequest(uri, HttpMethod.Post, body)
    }

    @DeleteMapping("/{bookId}/unlike", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteFromFavorites(
        @PathVariable
        bookId: Int
    ): ResponseEntity<Any> {
        logger.info("Deleting the book with id = $bookId from favorites")

        val uri = UriComponentsBuilder
            .fromUriString(favoritesServiceUri)
            .path("/favorites")
            .toUriString()
        val body = ObjectMapper().createObjectNode().apply {
            put("bookId", bookId)
        }
        return sendRequest(uri, HttpMethod.Delete, body)
    }

}