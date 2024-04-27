package com.example.bibliocat.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.bibliocat.model.Book

// Data Access Object for MyCollection table in the database to allow interaction with the table
// vaious database operations like insert, update, delete, select, etc. are defined here
@Dao
interface MyCollectionDao {

    @Insert
    suspend fun insert(book : Book)

    @Update
    suspend fun update(book : Book)

    @Delete
    suspend fun delete(book : Book)

    // get a book by its id
    @Query("SELECT * FROM my_collection WHERE bookId = :id")
    suspend fun getBookById(id:Int) : Book

    // get all items
    @Query("SELECT * FROM my_collection ORDER BY bookId ASC")
    fun getAllBooks() : LiveData<List<Book>>

    // get bookshelf items
    @Query("SELECT * FROM my_collection WHERE read = 0 AND wishlist = 0 ORDER BY bookId ASC")
    fun getBookShelf() : LiveData<List<Book>>

    // get wishlist items
    @Query("SELECT * FROM my_collection WHERE wishlist = 1 ORDER BY bookId ASC")
    fun getWishlist() : LiveData<List<Book>>

    // get read items
    @Query("SELECT * FROM my_collection WHERE read = 1 ORDER BY bookId ASC")
    fun getRead() : LiveData<List<Book>>

    // get count of all items
    @Query("SELECT COUNT(*) FROM my_collection")
    suspend fun getTotalCount() : Int

    // get count of bookshelf items
    @Query("SELECT COUNT(*) FROM my_collection WHERE read = 0 AND wishlist = 0")
    suspend fun getBookShelfCount() : Int

    // get count of wishlist items
    @Query("SELECT COUNT(*) FROM my_collection WHERE wishlist = 1")
    suspend fun getWishlistCount() : Int

    // get count of read items
    @Query("SELECT COUNT(*) FROM my_collection WHERE read = 1")
    suspend fun getReadCount() : Int

    // search for a book by keyword
    @Query("SELECT * FROM my_collection WHERE title LIKE '%' || :keyword || '%' OR author LIKE '%' " +
            "|| :keyword || '%' OR isbn LIKE '%' || :keyword || '%' OR publisher LIKE '%' || " +
            ":keyword || '%' OR edition LIKE '%' || :keyword || '%' OR pages LIKE '%' || :keyword " +
            "|| '%' OR genre LIKE '%' || :keyword || '%' OR year LIKE '%' || :keyword || '%' OR " +
            "price LIKE '%' || :keyword || '%'")
    fun search(keyword:String) : LiveData<List<Book>>

}