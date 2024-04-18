package com.example.bibliocat

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookShelfActivity : AppCompatActivity() {

    private lateinit var adapter: BookAdapter
    private lateinit var dbHelper: BookDbHelper
    private lateinit var backBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_shelf_activity)

        backBtn = findViewById(R.id.backBtn)
        dbHelper = BookDbHelper(this)

        val button = intent.getStringExtra("button")
        val searchQuery = intent.getStringExtra("searchQuery").toString()
        val bookList = when (button) {
            "booksread" -> dbHelper.getReadBooks()
            "wishlist" -> dbHelper.getWishlist()
            "search" -> dbHelper.getSearchResults(searchQuery)
            else -> dbHelper.getBookshelf()
        }

        val counterTextView = findViewById<TextView>(R.id.counterTextView)
        counterTextView.text = when (button) {
            "booksread" -> resources.getQuantityString(
                R.plurals.book_read_format,
                        bookList.size,
                bookList.size
            )

            "wishlist" -> resources.getQuantityString(
                R.plurals.wishlist_format,
                bookList.size,
                bookList.size
            )

            "search" -> resources.getQuantityString(
                R.plurals.book_found_format,
                bookList.size,
                bookList.size
            )

            else -> resources.getQuantityString(
                R.plurals.shelf_format,
                bookList.size,
                bookList.size
            )
        }

        val emptyView = findViewById<TextView>(R.id.emptyView)

        adapter = BookAdapter(bookList)
        val recyclerView = findViewById<RecyclerView>(R.id.bookRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        if (bookList.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
        }

        backBtn.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        val button = intent.getStringExtra("button")
        val searchQuery = intent.getStringExtra("searchQuery").toString()
        val bookList = when (button) {
            "booksread" -> dbHelper.getReadBooks()
            "wishlist" -> dbHelper.getWishlist()
            "search" -> dbHelper.getSearchResults(searchQuery)
            else -> dbHelper.getBookshelf()
        }

        val counterTextView = findViewById<TextView>(R.id.counterTextView)
        counterTextView.text = when (button) {
            "booksread" -> resources.getQuantityString(
                R.plurals.book_read_format,
                bookList.size,
                bookList.size
            )

            "wishlist" -> resources.getQuantityString(
                R.plurals.wishlist_format,
                bookList.size,
                bookList.size
            )

            "search" -> resources.getQuantityString(
                R.plurals.book_found_format,
                bookList.size,
                bookList.size
            )

            else -> resources.getQuantityString(
                R.plurals.shelf_format,
                bookList.size,
                bookList.size)
        }

        adapter.bookList = bookList
        adapter.notifyDataSetChanged()
    }
}
