package com.study.bookservice.controller

import com.study.bookservice.dto.BookDetails
import com.study.bookservice.dto.BookResponse
import com.study.bookservice.service.BookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.sleuth.Tracer
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/books")
class BookController @Autowired constructor(
    private val bookService: BookService
) {

    @GetMapping
    fun getBooks(
        @RequestParam(value = "offset", defaultValue = "0")
        offset: Int,
        @RequestParam(value = "limit", defaultValue = "25")
        limit: Int
    ): ResponseEntity<BookResponse> {
        val total = bookService.totalBooks
        val books = bookService.getAllBooks(offset, limit)
        val response = BookResponse(offset, limit, total, books)
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("{bookId}")
    fun getBookDetails(
        @PathVariable bookId: Int
    ): ResponseEntity<BookDetails> {
        val book = bookService.getBookById(bookId)
        return if (book == null) ResponseEntity.notFound().build()
        else ResponseEntity.ok().body(book)
    }
}