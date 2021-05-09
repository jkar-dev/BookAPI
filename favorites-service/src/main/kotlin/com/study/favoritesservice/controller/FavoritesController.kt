package com.study.favoritesservice.controller

import com.study.favoritesservice.service.FavoritesService
import com.study.favoritesservice.dto.Book
import com.study.favoritesservice.dto.Message
import com.study.favoritesservice.dto.Request
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/favorites")
class FavoritesController @Autowired constructor(
    private val service: FavoritesService
) {

    @GetMapping
    fun getAllFavorites(): ResponseEntity<List<Book>> {
        val books = service.getFavorites()
        return ResponseEntity.ok().body(books)
    }

    @PostMapping
    fun addToFavorites(
        @RequestBody
        addRequest: Request
    ): ResponseEntity<Message> {
        val result = service.tryToAddToFavorites(addRequest.bookId)
        return ResponseEntity.ok().body(Message(result))
    }

    @DeleteMapping
    fun deleteFromFavorites(
        @RequestBody
        deleteRequest: Request
    ): ResponseEntity<Message> {
        val result = service.tryToDeleteFromFavorites(deleteRequest.bookId)
        return ResponseEntity.ok().body(Message(result))
    }

}