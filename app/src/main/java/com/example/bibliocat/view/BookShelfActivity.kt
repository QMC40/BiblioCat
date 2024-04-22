package com.example.bibliocat.view

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bibliocat.R
import com.example.bibliocat.adapter.BookAdapter
import com.example.bibliocat.databinding.ActivityBookShelfBinding
import com.example.bibliocat.model.Book
import com.example.bibliocat.viewmodel.BookViewModel
import kotlinx.coroutines.launch

class BookShelfActivity : AppCompatActivity() {

    private lateinit var bookViewModel: BookViewModel
    private lateinit var bookShelfBinding: ActivityBookShelfBinding
    private lateinit var bookAdapter: BookAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView
    private lateinit var bookList: LiveData<List<Book>>
    private var button: String? = null
    private var bookListSize: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookShelfBinding = ActivityBookShelfBinding.inflate(layoutInflater)
        setContentView(bookShelfBinding.root)

        bookViewModel = BookViewModel(application)
        bookAdapter = BookAdapter(this)
        recyclerView = bookShelfBinding.bookRecyclerView
        emptyView = bookShelfBinding.emptyView

        button = intent.getStringExtra("button")
        val searchQuery = intent.getStringExtra("searchQuery").toString()

        bookList = when (button) {
            "booksread" -> bookViewModel.getReadBooks()
            "wishlist" -> bookViewModel.getWishlist()
            "search" -> bookViewModel.getSearchResults(searchQuery)
            else -> bookViewModel.getBookshelf()
        }

        updateCounter()

        // Set the onClickListener for the back button
        bookShelfBinding.backBtn.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        updateBookList()
        updateEmptyView()

        val searchQuery = intent.getStringExtra("searchQuery").toString()

        val bookList = when (button) {
            "booksread" -> bookViewModel.getReadBooks()
            "wishlist" -> bookViewModel.getWishlist()
            "search" -> bookViewModel.getSearchResults(searchQuery)
            else -> bookViewModel.getBookshelf()
        }

        lifecycleScope.launch {

            observeBookListSize(bookList) { size ->
                bookListSize = size
            }
            bookShelfBinding.counterTextView.text = when (button) {
                "booksread" -> resources.getQuantityString(
                    R.plurals.book_read_format,
                    bookListSize,
                    bookListSize
                )

                "wishlist" -> resources.getQuantityString(
                    R.plurals.wishlist_format,
                    bookListSize,
                    bookListSize
                )

                "search" -> resources.getQuantityString(
                    R.plurals.book_found_format,
                    bookListSize,
                    bookListSize
                )

                else -> resources.getQuantityString(
                    R.plurals.shelf_format,
                    bookListSize,
                    bookListSize
                )
            }

            bookList.observe(this@BookShelfActivity, androidx.lifecycle.Observer { books ->
                bookAdapter = BookAdapter(this@BookShelfActivity)
                bookAdapter.setBooks(books)
                bookShelfBinding.bookRecyclerView.layoutManager =
                    LinearLayoutManager(this@BookShelfActivity)
                bookShelfBinding.bookRecyclerView.adapter = bookAdapter
            })

            val emptyView = findViewById<TextView>(R.id.emptyView)

            observeBookListIsEmpty(bookList) { isEmpty ->
                if (isEmpty) {
                    recyclerView.visibility = View.GONE
                    emptyView.visibility = View.VISIBLE
                } else {
                    recyclerView.visibility = View.VISIBLE
                    emptyView.visibility = View.GONE
                }
            }

            bookAdapter.notifyDataSetChanged()
        }
    }

    private fun updateBookList() {
        bookList.observe(this, androidx.lifecycle.Observer { books ->
            bookAdapter.setBooks(books)
            bookAdapter.notifyDataSetChanged()
        })
    }

    private fun updateEmptyView() {
        observeBookListIsEmpty(bookList) { isEmpty ->
            if (isEmpty) {
                recyclerView.visibility = View.GONE
                emptyView.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                emptyView.visibility = View.GONE
            }
        }
    }

    private fun updateCounter() {

        observeBookListSize(bookList) { size ->
            bookListSize = size
            bookShelfBinding.counterTextView.text = when (button) {
                "booksread" -> resources.getQuantityString(
                    R.plurals.book_read_format,
                    bookListSize,
                    bookListSize
                )

                "wishlist" -> resources.getQuantityString(
                    R.plurals.wishlist_format,
                    bookListSize,
                    bookListSize
                )

                "search" -> resources.getQuantityString(
                    R.plurals.book_found_format,
                    bookListSize,
                    bookListSize
                )

                else -> resources.getQuantityString(
                    R.plurals.shelf_format,
                    bookListSize,
                    bookListSize
                )
            }
        }
    }

    private fun observeBookListSize(bookList: LiveData<List<Book>>, onResult: (Int) -> Unit) {
        bookList.observe(this, androidx.lifecycle.Observer { books ->
            onResult(books.size)
        })
    }

    private fun observeBookListIsEmpty(
        bookList: LiveData<List<Book>>,
        onResult: (Boolean) -> Unit
    ) {
        bookList.observe(this, androidx.lifecycle.Observer { books ->
            onResult(books.isEmpty())
        })
    }
}


