@file:Suppress("declaration", "unused")

package com.example.bibliocat

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class AddBooksActivity : AppCompatActivity() {

    private val dbHelper = BookDbHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_books_activity)

        val count = dbHelper.getBooksCount()
        val countTextView = findViewById<TextView>(R.id.bookCounterTextView)

        countTextView.text = resources.getQuantityString(R.plurals.book_count_format, count, count)
    }

    override fun onResume() {
        super.onResume()

        val count = dbHelper.getBooksCount()
        val countTextView = findViewById<TextView>(R.id.bookCounterTextView)
        countTextView.text = resources.getQuantityString(R.plurals.book_count_format, count, count)
    }

    fun updateCounter() {
        val bookCounterTextView = findViewById<TextView>(R.id.bookCounterTextView)
        val count = dbHelper.getBooksCount()
        bookCounterTextView.text = resources.getQuantityString(R.plurals.book_count_format, count, count)
    }

    fun enterBookManually(view: View) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.add_book_fragment_container, AddBookFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun getCover(view: View) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(R.id.add_book_fragment_container, CameraFragment())
    transaction.addToBackStack(null)
    transaction.commit()
}

    fun searchOnline(view: View) {
        print("Searching online")
    }

    fun scanBarcode(view: View) {
        print("Scanning barcode")
    }
}
