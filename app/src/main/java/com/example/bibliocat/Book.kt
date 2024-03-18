package com.example.bibliocat

data class Book(
    val id: Int = -1,
    val title: String,
    val author: String,
    val isbn: String,
    val publisher: String,
    val edition: String,
    val pages: String,
    val genre: String,
    val year: String,
    val price: String
)