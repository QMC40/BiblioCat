package com.example.bibliocat.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// This class represents the Book entity in the database holding the details of the book and a
// cover image as a string. The class is annotated with @Entity to specify the table name in the
// database. The class also has a primary key field annotated with @PrimaryKey to specify the
// primary key of the table.
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