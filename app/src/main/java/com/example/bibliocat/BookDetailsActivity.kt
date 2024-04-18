package com.example.bibliocat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class BookDetailsActivity : AppCompatActivity() {
    private lateinit var titleTextView: TextView
    private lateinit var authorTextView: TextView
    private lateinit var genreTextView: TextView
    private lateinit var isbnTextView: TextView
    private lateinit var publisherTextView: TextView
    private lateinit var editionTextView: TextView
    private lateinit var pagesTextView: TextView
    private lateinit var yearTextView: TextView
    private lateinit var priceTextView: TextView
    private lateinit var ratingBarView: RatingBar
    private lateinit var readCheckBoxView: CheckBox
    private lateinit var wishlistCheckBoxView: CheckBox
    private lateinit var coverImageView: ImageView
    private lateinit var editBtn: Button
    private lateinit var deleteBtn: Button
    private lateinit var backBtn: Button

    private var bookId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        titleTextView = findViewById(R.id.titleTextView)
        authorTextView = findViewById(R.id.authorTextView)
        genreTextView = findViewById(R.id.genreTextView)
        isbnTextView = findViewById(R.id.isbnTextView)
        publisherTextView = findViewById(R.id.publisherTextView)
        editionTextView = findViewById(R.id.editionTextView)
        pagesTextView = findViewById(R.id.pagesTextView)
        yearTextView = findViewById(R.id.yearTextView)
        priceTextView = findViewById(R.id.priceTextView)
        ratingBarView = findViewById(R.id.ratingBar)
        readCheckBoxView = findViewById(R.id.readSwitch)
        wishlistCheckBoxView = findViewById(R.id.wishlistSwitch)
        coverImageView = findViewById(R.id.coverImageView)
        editBtn = findViewById(R.id.editButton)
        deleteBtn = findViewById(R.id.deleteButton)
        backBtn = findViewById(R.id.backButton)

        bookId = intent.getIntExtra("bookId", -1)
        if (bookId != -1) {
            val dbHelper = BookDbHelper(this)
            val book = dbHelper.getBook(bookId)

            titleTextView.text = book.title
            authorTextView.text = book.author
            isbnTextView.text = book.isbn
            genreTextView.text = book.genre
            publisherTextView.text = book.publisher
            editionTextView.text = book.edition
            pagesTextView.text = book.pages.toString()
            yearTextView.text = book.year.toString()
            priceTextView.text = book.price.toString()
            ratingBarView.rating = book.rating.toFloat()
            readCheckBoxView.isChecked = book.read
            wishlistCheckBoxView.isChecked = book.wishlist

        }

        editBtn.setOnClickListener {
            val intent = Intent(this, EditBookActivity::class.java)
            intent.putExtra("isEditing", true)
            intent.putExtra("bookId", bookId)
            startActivity(intent)
        }

        deleteBtn.setOnClickListener {
            Log.d("BookDetailsActivity", "Delete book")
            // add confirmation dialog to prevent accidental deletion of book
            AlertDialog.Builder(this)
                .setTitle("Delete Book")
                .setMessage("Are you sure you want to delete this book?")
                .setPositiveButton("Yes") { _, _ ->
                    val dbHelper = BookDbHelper(this)
                    dbHelper.deleteBook(bookId)
                    finish()
                }
                .setNegativeButton("No", null)
                .show()
        }

        backBtn.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        val dbHelper = BookDbHelper(this)
        val book = dbHelper.getBook(bookId)
        titleTextView.text = book.title
        authorTextView.text = book.author
        isbnTextView.text = book.isbn
        publisherTextView.text = book.publisher
        editionTextView.text = book.edition
        pagesTextView.text = book.pages
        yearTextView.text = book.year
        priceTextView.text = book.price
        ratingBarView.rating = book.rating.toFloat()
        readCheckBoxView.isChecked = book.read
        wishlistCheckBoxView.isChecked = book.wishlist
        genreTextView.text = book.genre
    }
}