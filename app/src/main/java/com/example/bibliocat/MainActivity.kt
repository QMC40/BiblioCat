@file:Suppress("unused")

package com.example.bibliocat


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var addBooksBtn: Button
    private lateinit var bookshelfBtn: Button
    private lateinit var shareBtn: Button
    private lateinit var wishlistBtn: Button
    private lateinit var whatAmIReadingBtn: Button
    private val dbHelper = BookDbHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val count = dbHelper.getBooksCount()
        val countTextView = findViewById<TextView>(R.id.bookCounterTextView)
        countTextView.text = resources.getQuantityString(R.plurals.book_count_format, count, count)

        addBooksBtn = findViewById(R.id.addBooksBtn)
        bookshelfBtn = findViewById(R.id.bookshelf)
        shareBtn = findViewById(R.id.shareBtn)
        wishlistBtn = findViewById(R.id.wishlistBtn)
        whatAmIReadingBtn = findViewById(R.id.whatAmIReadingBtn)

        addBooksBtn.setOnClickListener {
            openAddBooksActivity(it)
        }

        bookshelfBtn.setOnClickListener {
            openBookshelfActivity(it)
        }

        shareBtn.setOnClickListener {
            openShareActivity(it)
        }

        wishlistBtn.setOnClickListener {
            openWishlistActivity(it)
        }

        whatAmIReadingBtn.setOnClickListener {
            openWhatAmIReadingActivity(it)
        }
    }

    override fun onResume() {
        super.onResume()

        val count = dbHelper.getBooksCount()
        val countTextView = findViewById<TextView>(R.id.bookCounterTextView)
        countTextView.text = resources.getQuantityString(R.plurals.book_count_format, count, count)
    }

    fun openAddBooksActivity(view: View) {
        // Handle addBooksBtn click
        val intent = Intent(this, AddBooksActivity::class.java)
        startActivity(intent)
    }

    fun openBookshelfActivity(view: View) {
        // Handle bookshelfBtn click
        val intent = Intent(this, BookshelfActivity::class.java)
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

    fun openWhatAmIReadingActivity(view: View) {
        // Handle whatAmIReadingBtn click
        val intent = Intent(this, WhatAmIReadingActivity::class.java)
        startActivity(intent)
    }
}
