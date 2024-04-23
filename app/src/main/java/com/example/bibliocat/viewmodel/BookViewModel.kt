package com.example.bibliocat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.bibliocat.model.Book
import com.example.bibliocat.repository.MyBookRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: MyBookRepository
    private var bookList: LiveData<List<Book>>

    init {
        repository = MyBookRepository(application)
        bookList = repository.getAllCollection()
    }

    fun insert(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(book)
    }

    fun update(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(book)
    }

    fun delete(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(book)
    }

    suspend fun getBookById(id: Int): Book {
        return repository.getBookById(id)
    }

    fun getAllBooks(): LiveData<List<Book>> {
        return bookList
    }

    fun getReadBooks(): LiveData<List<Book>> {
        return repository.getRead()

    }

    fun getWishlist(): LiveData<List<Book>> {
        return repository.getWishlist()
    }

    fun getBookshelf(): LiveData<List<Book>> {
        return repository.getBookShelf()
    }

    suspend fun getTotalCount(): Int {
        return repository.getTotalCount()
    }

    suspend fun getBookShelfCount(): Int {
        return repository.getBookShelfCount()
    }

    suspend fun getWishlistCount(): Int {
        return repository.getWishlistCount()
    }

    suspend fun getReadCount(): Int {
        return repository.getReadCount()
    }

    fun getSearchResults(searchQuery: String): LiveData<List<Book>> {
        return repository.getSearchResults(searchQuery)
    }
}
