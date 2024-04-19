@file:Suppress("unused", "never used")

package com.example.bibliocat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var searchField: EditText
    private lateinit var searchBtn: Button
    private lateinit var addBooksBtn: Button
    private lateinit var bookshelfBtn: Button
    private lateinit var shareBtn: Button
    private lateinit var wishlistBtn: Button
    private lateinit var whatAmIReadingBtn: Button
    private val dbHelper = BookDb(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        searchField = findViewById(R.id.searchField)
        searchBtn = findViewById(R.id.searchBtn)
        val count = dbHelper.getBooksCount()
        val countTextView = findViewById<TextView>(R.id.bookCounterTextView)
        countTextView.text = resources.getQuantityString(R.plurals.shelf_format, count, count)

        addBooksBtn = findViewById(R.id.addBooksBtn)
        bookshelfBtn = findViewById(R.id.bookshelf)
        shareBtn = findViewById(R.id.shareBtn)
        wishlistBtn = findViewById(R.id.wishlistBtn)
        whatAmIReadingBtn = findViewById(R.id.whatAmIReadingBtn)

        // Set up the click listeners
        searchBtn.setOnClickListener {
            // collect the search query
            val searchQuery = searchField.text.toString()
            // check if the search field is empty
            if (searchQuery.isEmpty()) {
                // prompt the user to enter a search query
                searchField.error = "Please enter a search query"
                // return early
                return@setOnClickListener
            } else {
                // search for books
                searchForBooks(searchQuery)
            }
        }

        addBooksBtn.setOnClickListener {
            openAddBooksActivity(it)
        }

        bookshelfBtn.setOnClickListener {
            openBookShelfActivity(it)
        }

        shareBtn.setOnClickListener {
            openShareActivity(it)
        }

        wishlistBtn.setOnClickListener {
            openWishlistActivity(it)
        }

        whatAmIReadingBtn.setOnClickListener {
            openBooksReadActivity(it)
        }
    }

    override fun onResume() {
        super.onResume()
        val count = dbHelper.getTotalCount()
        val countTextView = findViewById<TextView>(R.id.bookCounterTextView)
        // Refresh the book count
        countTextView.text = resources.getQuantityString(R.plurals.shelf_format, count, count)
        // Clear the search field
        searchField.text.clear()
    }

    fun openAddBooksActivity(view: View) {
        // Handle addBooksBtn click
        val intent = Intent(this, AddBooksActivity::class.java)
        startActivity(intent)
    }

    private fun openBookShelfActivity(view: View) {
        // Handle bookshelfBtn click
        val intent = Intent(this, BookShelfActivity::class.java)
        intent.putExtra("button", "bookshelf")
        startActivity(intent)
    }

    fun openShareActivity(view: View) {
        // Handle shareBtn click
        val intent = Intent(this, ShareActivity::class.java)
        startActivity(intent)
    }

    fun openWishlistActivity(view: View) {
        // Handle wishlistBtn click
        val intent = Intent(this, BookShelfActivity::class.java)
        intent.putExtra("button", "wishlist")
        startActivity(intent)
    }

    private fun openBooksReadActivity(view: View) {
        // Handle whatAmIReadingBtn click
        val intent = Intent(this, BookShelfActivity::class.java)
        intent.putExtra("button", "booksread")
        startActivity(intent)
    }

    private fun searchForBooks(searchQuery: String) {
        val intent = Intent(this, BookShelfActivity::class.java)
        intent.putExtra("button", "search")
        intent.putExtra("searchQuery", searchQuery)
        startActivity(intent)
    }
}
