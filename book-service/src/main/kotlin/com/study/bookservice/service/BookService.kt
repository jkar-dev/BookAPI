package com.study.bookservice.service

import com.study.bookservice.dao.BookDao
import com.study.bookservice.dto.Book
import com.study.bookservice.dto.BookDetails
import com.study.bookservice.extensions.mapToDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class BookService(private val bookDao: BookDao) {

    val totalBooks = bookDao.getCountOfAllBooks()

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun getAllBooks(offset: Int, limit: Int): List<Book> {
        val books = bookDao.getAllBooksShort(offset, limit)
        logger.info("Books count ${books.size}")
        return books.map { it.mapToDto() }
    }

    fun getBookById(id: Int): BookDetails? {
        val book = bookDao.getBookById(id)
        logger.info("Book with id ${id}: ${book?.title}")
        return book?.mapToDto()
    }
}