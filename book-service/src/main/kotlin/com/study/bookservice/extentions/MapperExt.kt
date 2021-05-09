package com.study.bookservice.extentions

import com.study.bookservice.dto.Book
import com.study.bookservice.dto.BookDetails
import com.study.bookservice.entity.BookDetailsEntity
import com.study.bookservice.entity.BookEntity

fun BookEntity.mapToDto(): Book = Book(id, title, author)
fun BookDetailsEntity.mapToDto(): BookDetails =
    BookDetails(id, title, author, description, pageCount, publisher, publishDate)

fun Book.mapToEntity(): BookEntity = BookEntity(id, title, author)
fun BookDetails.mapToEntity(): BookDetailsEntity =
    BookDetailsEntity(id, title, author, description, pageCount, publisher, publishDate)