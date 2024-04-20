@file:Suppress("declaration", "unused")

package com.example.bibliocat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class AddBooksActivity : AppCompatActivity() {

    private val dbHelper = BookDb(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        val count = dbHelper.getBooksCount()
        val countTextView = findViewById<TextView>(R.id.bookCounterTextView)

        countTextView.text = resources.getQuantityString(R.plurals.shelf_format, count, count)
    }

    override fun onResume() {
        super.onResume()

        val count = dbHelper.getBooksCount()
        val countTextView = findViewById<TextView>(R.id.bookCounterTextView)
        countTextView.text = resources.getQuantityString(R.plurals.shelf_format, count, count)
    }

    fun updateCounter() {
        val bookCounterTextView = findViewById<TextView>(R.id.bookCounterTextView)
        val count = dbHelper.getBooksCount()
        bookCounterTextView.text = resources.getQuantityString(R.plurals.shelf_format, count, count)
    }

    fun enterBookManually(view: View) {
        // Start the EditBookActivity in add mode
        val intent = Intent(this, EditBookActivity::class.java)
        intent.putExtra("isEditing", false)
        startActivity(intent)
    }

    // TODO: remove and refactor to click on the image in the edit activity to change the cover
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

    fun back(view: View) {
        finish()
    }
}
