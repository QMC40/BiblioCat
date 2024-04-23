package com.example.bibliocat.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "my_collection")
data class Book(

    val title: String? = "",
    val author: String? = "",
    val isbn: String? = "",
    val publisher: String? = "",
    val edition: String? = "",
    val pages: String? = "",
    val genre: String? = "",
    val year: String? = "",
    val price: String? = "",
    val wishlist: Boolean = false,
    val rating: Double = 0.0,
    val read: Boolean = false,
    val coverImageAsString: String
){
    @PrimaryKey(autoGenerate = true)
    var bookId = 0
}