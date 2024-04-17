@file:Suppress("unused")

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
    private val dbHelper = BookDbHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        searchField = findViewById(R.id.searchField)
        searchBtn = findViewById(R.id.searchBtn)
        val count = dbHelper.getBooksCount()
        val countTextView = findViewById<TextView>(R.id.bookCounterTextView)
        countTextView.text = resources.getQuantityString(R.plurals.book_count_format, count, count)

        addBooksBtn = findViewById(R.id.addBooksBtn)
        bookshelfBtn = findViewById(R.id.bookshelf)
        shareBtn = findViewById(R.id.shareBtn)
        wishlistBtn = findViewById(R.id.wishlistBtn)
        whatAmIReadingBtn = findViewById(R.id.whatAmIReadingBtn)

        searchBtn.setOnClickListener {
            val searchQuery = searchField.text.toString()
            searchForBooks(searchQuery)
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
        val count = dbHelper.getBooksCount()
        val countTextView = findViewById<TextView>(R.id.bookCounterTextView)
        // Refresh the book count
        countTextView.text = resources.getQuantityString(R.plurals.book_count_format, count, count)
        // Clear the search field
        searchField.text.clear()
    }

    fun openAddBooksActivity(view: View) {
        // Handle addBooksBtn click
        val intent = Intent(this, AddBooksActivity::class.java)
        startActivity(intent)
    }

    fun openBookShelfActivity(view: View) {
        // Handle bookshelfBtn click
        val intent = Intent(this, BookShelfActivity::class.java)
        startActivity(intent)
    }

    fun openShareActivity(view: View) {
        // Handle shareBtn click
        val intent = Intent(this, ShareActivity::class.java)
        startActivity(intent)
    }

    fun openWishlistActivity(view: View) {
        // Handle wishlistBtn click
        val intent = Intent(this, WishlistActivity::class.java)
        startActivity(intent)
    }

    fun openBooksReadActivity(view: View) {
        // Handle whatAmIReadingBtn click
        val intent = Intent(this, BooksReadActivity::class.java)
        startActivity(intent)
    }

    private fun searchForBooks(searchQuery: String) {
        val dbHelper = BookDbHelper(this)
        val books = dbHelper.searchBooks(searchQuery)
        val intent = Intent(this, SearchResultsActivity::class.java)
        intent.putParcelableArrayListExtra("books", ArrayList(books))
        startActivity(intent)
    }
}
