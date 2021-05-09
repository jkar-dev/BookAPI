package com.study.favoritesservice.extentions

import com.study.favoritesservice.dto.Book
import com.study.favoritesservice.entity.BookEntity

fun BookEntity.mapToDto(): Book = Book(id, title, author)
fun Book.mapToEntity(): BookEntity = BookEntity(id, title, author)