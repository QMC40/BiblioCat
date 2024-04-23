package com.example.bibliocat.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.bibliocat.model.Book
import com.example.bibliocat.room.MyCollectionDao
import com.example.bibliocat.room.MyCollectionDatabase

class MyBookRepository(application: Application) {

    private var myCollectionDao : MyCollectionDao
    private var collectionList : LiveData<List<Book>>

    init {
        val database = MyCollectionDatabase.getDatabaseInstance(application)
        myCollectionDao = database.myCollectionDao()
        collectionList = myCollectionDao.getAllBooks()
    }

    suspend fun insert(book: Book){
        myCollectionDao.insert(book)
    }
    suspend fun update(book: Book){
        myCollectionDao.update(book)
    }
    suspend fun delete(book: Book){
        myCollectionDao.delete(book)
    }

    suspend fun getBookById(id:Int):Book{
        return myCollectionDao.getBookById(id)
    }

    fun getAllCollection() : LiveData<List<Book>>{
        return collectionList
    }

    fun getBookShelf(): LiveData<List<Book>> {
        return myCollectionDao.getBookShelf()
    }

    fun getWishlist(): LiveData<List<Book>> {
        return myCollectionDao.getWishlist()
    }

    fun getRead(): LiveData<List<Book>> {
        return myCollectionDao.getRead()

    }

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

    fun getSearchResults(searchQuery: String): LiveData<List<Book>> {
        return myCollectionDao.search(searchQuery.toString())
    }
}