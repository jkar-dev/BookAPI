package com.study.bookservice.entity

data class BookDetailsEntity(
    val id: Int = 0,
    val title: String = "",
    val author: String = "",
    val description: String = "",
    val pageCount: Int = 0,
    val publisher: String = "",
    val publishDate: String = ""
)
