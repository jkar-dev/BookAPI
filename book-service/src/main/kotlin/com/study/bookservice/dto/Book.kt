package com.study.bookservice.dto

data class Book(
    val id : Int,
    val title : String,
    val author : String
)

data class BookResponse(
    val offset : Int,
    val limit : Int,
    val total : Int,
    val books : List<Book>
)