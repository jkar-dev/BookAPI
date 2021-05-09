package com.study.bookservice.dto

data class BookDetails(
    val id: Int,
    val title: String,
    val author: String,
    val description: String,
    val pageCount: Int,
    val publisher: String,
    val publishDate: String
)