package com.example.bibliocat.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.bibliocat.model.Book
import com.example.bibliocat.room.MyCollectionDao
import com.example.bibliocat.room.MyCollectionDatabase

// Repository class for MyBookFragment to interact with the database and provide data to the
// ViewModel This class is responsible for handling data operations. It provides a clean API to the
// rest of the app to work with the book data.
class MyBookRepository(application: Application) {

    // DAO object to interact with the database
    private var myCollectionDao : MyCollectionDao
    // LiveData object to hold the list of books
    private var collectionList : LiveData<List<Book>>

    // Initialize the DAO object and LiveData object
    init {
        val database = MyCollectionDatabase.getDatabaseInstance(application)
        myCollectionDao = database.myCollectionDao()
        collectionList = myCollectionDao.getAllBooks()
    }

    // Functions to perform CRUD operations on the database
    suspend fun insert(book: Book){
        myCollectionDao.insert(book)
    }
    suspend fun update(book: Book){
        myCollectionDao.update(book)
    }
    suspend fun delete(book: Book){
        myCollectionDao.delete(book)
    }

    // Function to get a book by its ID
    suspend fun getBookById(id:Int):Book{
        return myCollectionDao.getBookById(id)
    }

    // Function to get all books from the database
    fun getAllCollection() : LiveData<List<Book>>{
        return collectionList
    }

    // Functions to get the bookshelf, wishlist and read list
    fun getBookShelf(): LiveData<List<Book>> {
        return myCollectionDao.getBookShelf()
    }

    fun getWishlist(): LiveData<List<Book>> {
        return myCollectionDao.getWishlist()
    }

    fun getRead(): LiveData<List<Book>> {
        return myCollectionDao.getRead()

    }

    // Functions to get the count of total books in the collection, in the bookshelf, the wishlist
    // and the read list
    suspend fun getTotalCount(): Int {
        return myCollectionDao.getTotalCount()

    }

    suspend fun getBookShelfCount(): Int {
        return myCollectionDao.getBookShelfCount()

    }

    suspend fun getWishlistCount(): Int {
        return myCollectionDao.getWishlistCount()

    }

    suspend fun getReadCount(): Int {
        return myCollectionDao.getReadCount()

    }

    // Function to search for books in the collection containing the entered keyword
    fun getSearchResults(searchQuery: String): LiveData<List<Book>> {
        return myCollectionDao.search(searchQuery.toString())
    }
}