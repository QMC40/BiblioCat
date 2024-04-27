package com.example.bibliocat.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.bibliocat.databinding.ActivityBookDetailsBinding
import com.example.bibliocat.model.Book
import com.example.bibliocat.util.ConvertImage
import com.example.bibliocat.viewmodel.BookViewModel
import kotlinx.coroutines.launch

// This activity displays the details of a book that the user has selected
class BookDetailsActivity : AppCompatActivity() {

    // declare variables
    private lateinit var bookViewModel: BookViewModel
    private lateinit var bookDetailsBinding: ActivityBookDetailsBinding
    private var coverImageAsString: String = ""
    private var book: Book? = null
    private var bookId: Int = -1

    // create the activity and set the content view to the book details layout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookDetailsBinding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(bookDetailsBinding.root)

        // get the book id from the calling intent
        bookId = intent.getIntExtra("bookId", -1)

        // get the book view model and get the book from the database using the book id
        bookViewModel = ViewModelProvider(this)[BookViewModel::class.java]

        // if the book id is not -1, then set the text views to the book details and set the cover
        // image.  This is done by getting the book from the database using the book id and setting
        // the text views to the book details.  The cover image is pulled from the database as a
        // string and converted to a bitmap.  The bitmap is then set as the cover image.  All this
        // is done in a coroutine.
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

            // set the on click listeners for the edit, delete, and back buttons
            bookDetailsBinding.editBtn.setOnClickListener {
                val intent = Intent(this, EditBookActivity::class.java)
                // pass the book id to the edit book activity so it knows which book to edit and
                intent.putExtra("bookId", bookId)
                startActivity(intent)
            }

            // set the on click listener for the delete button.  This will delete the book from the
            // database and return to the main activity
            bookDetailsBinding.deleteBtn.setOnClickListener {
                // confirmation dialog to prevent accidental deletion of book
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

            // set the on click listener for the back button.  This will return to the main activity
            bookDetailsBinding.backBtn.setOnClickListener {
                finish()
            }

        } else {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        // get the book view model and get the book from the database using the book id in a
        // coroutine and set the book details layout to display the book details and any changes
        // that may have been made to the book
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
