package com.example.bibliocat.view

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.bibliocat.databinding.ActivityBookDetailsBinding
import com.example.bibliocat.model.Book
import com.example.bibliocat.util.ConvertImage
import com.example.bibliocat.viewmodel.BookViewModel
import kotlinx.coroutines.launch

class BookDetailsActivity : AppCompatActivity() {

    private lateinit var bookViewModel: BookViewModel
    private lateinit var bookDetailsBinding: ActivityBookDetailsBinding
    private lateinit var coverImage: Bitmap
    private var coverImageAsString: String = ""
    private var book: Book? = null
    private var bookId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookDetailsBinding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(bookDetailsBinding.root)

        bookId = intent.getIntExtra("bookId", -1)

        bookViewModel = ViewModelProvider(this)[BookViewModel::class.java]

        if (bookId != -1) {
            lifecycleScope.launch {
                book = bookViewModel.getBookById(bookId)
                bookDetailsBinding.titleTextView.text = book!!.title
                bookDetailsBinding.authorTextView.text = book!!.author
                bookDetailsBinding.isbnTextView.text = book!!.isbn
                bookDetailsBinding.genreTextView.text = book!!.genre
                bookDetailsBinding.publisherTextView.text = book!!.publisher
                bookDetailsBinding.editionTextView.text = book!!.edition
                bookDetailsBinding.pagesTextView.text = book!!.pages.toString()
                bookDetailsBinding.yearTextView.text = book!!.year.toString()
                bookDetailsBinding.priceTextView.text = book!!.price.toString()
                bookDetailsBinding.ratingBar.rating = book!!.rating.toFloat()
                bookDetailsBinding.readSwitch.isChecked = book!!.read
                bookDetailsBinding.wishlistSwitch.isChecked = book!!.wishlist

                // pull the cover image from the database as a string and convert it to a bitmap
                // then set the bitmap as the cover image
                coverImageAsString = book!!.coverImageAsString
                val coverAsBitmap = ConvertImage.convertToBitmap(coverImageAsString)

                bookDetailsBinding.coverImageView.setImageBitmap(coverAsBitmap)
            }

            bookDetailsBinding.editBtn.setOnClickListener {
                val intent = Intent(this, EditBookActivity::class.java)
                intent.putExtra("isEditing", true)
                intent.putExtra("bookId", bookId)
                startActivity(intent)
            }

            bookDetailsBinding.deleteBtn.setOnClickListener {
                // add confirmation dialog to prevent accidental deletion of book
                AlertDialog.Builder(this)
                    .setTitle("Delete Book")
                    .setMessage("Are you sure you want to delete this book?")
                    .setPositiveButton("Yes") { _, _ ->
                        book?.let { it1 -> bookViewModel.delete(it1) }
                        finish()
                    }
                    .setNegativeButton("No", null)
                    .show()
            }

            bookDetailsBinding.backBtn.setOnClickListener {
                finish()
            }

        } else {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        if (bookId != -1) {
            lifecycleScope.launch {
                book = bookViewModel.getBookById(bookId)
                bookDetailsBinding.titleTextView.text = book!!.title
                bookDetailsBinding.authorTextView.text = book!!.author
                bookDetailsBinding.isbnTextView.text = book!!.isbn
                bookDetailsBinding.genreTextView.text = book!!.genre
                bookDetailsBinding.publisherTextView.text = book!!.publisher
                bookDetailsBinding.editionTextView.text = book!!.edition
                bookDetailsBinding.pagesTextView.text = book!!.pages.toString()
                bookDetailsBinding.yearTextView.text = book!!.year.toString()
                bookDetailsBinding.priceTextView.text = book!!.price.toString()
                bookDetailsBinding.ratingBar.rating = book!!.rating.toFloat()
                bookDetailsBinding.readSwitch.isChecked = book!!.read
                bookDetailsBinding.wishlistSwitch.isChecked = book!!.wishlist

                // pull the cover image from the database as a string and convert it to a bitmap
                // then set the bitmap as the cover image
                coverImageAsString = book!!.coverImageAsString
                val coverAsBitmap = ConvertImage.convertToBitmap(coverImageAsString)

                bookDetailsBinding.coverImageView.setImageBitmap(coverAsBitmap)
            }

        } else {
            finish()
        }
    }
}
