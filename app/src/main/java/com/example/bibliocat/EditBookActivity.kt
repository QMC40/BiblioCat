package com.example.bibliocat

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditBookActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var authorEditText: EditText
    private lateinit var genreTextView: TextView
    private lateinit var isbnEditText: EditText
    private lateinit var publisherEditText: EditText
    private lateinit var editionEditText: EditText
    private lateinit var pagesEditText: EditText
    private lateinit var yearEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var wishListCheckBox: CheckBox
    private lateinit var readCheckBox: CheckBox
    private lateinit var ratingBar: RatingBar
    private lateinit var coverImageView: ImageView
    private lateinit var saveButton: Button
    private lateinit var backButton: Button

    // Initialize the bookId to -1, which is an invalid id, isEditing to false and the dbHelper
    // to the BookDb object
    private var bookId: Int = -1
    private var isEditing: Boolean = false
    private var dbHelper = BookDb(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_book)

        // Find all the views in the layout and assign them to the corresponding variables
        titleEditText = findViewById(R.id.titleEditText)
        authorEditText = findViewById(R.id.authorEditText)
        genreTextView = findViewById(R.id.genreEditText)
        isbnEditText = findViewById(R.id.isbnEditText)
        publisherEditText = findViewById(R.id.publisherEditText)
        editionEditText = findViewById(R.id.editionEditText)
        pagesEditText = findViewById(R.id.pagesEditText)
        yearEditText = findViewById(R.id.yearEditText)
        priceEditText = findViewById(R.id.priceEditText)
        ratingBar = findViewById(R.id.ratingBar)
        wishListCheckBox = findViewById(R.id.wishlistSwitch)
        readCheckBox = findViewById(R.id.readSwitch)
        saveButton = findViewById(R.id.saveButton)
        backButton = findViewById(R.id.backButton)

        // Get the intent that started this activity and check if the user is editing a book or
        // adding a new one
        isEditing = intent.getBooleanExtra("isEditing", false)
        bookId = intent.getIntExtra("bookId", -1)

        // If the user is editing a book, load the book's details into the form
        if (isEditing) {
            // If the bookId is -1, then no book id was passed to the activity, return an error
            if (bookId == -1) {
                Toast.makeText(this, "Error: No book with that id in DB", Toast.LENGTH_SHORT).show()
                return
            }

            // Get the book from the database using the bookId for editing
            val book = dbHelper.getBook(bookId)

            titleEditText.setText(book.title)
            authorEditText.setText(book.author)
            genreTextView.text = book.genre
            isbnEditText.setText(book.isbn)
            publisherEditText.setText(book.publisher)
            editionEditText.setText(book.edition)
            pagesEditText.setText(book.pages.toString())
            yearEditText.setText(book.year.toString())
            priceEditText.setText(book.price.toString())
            ratingBar.rating = book.rating.toFloat()
            wishListCheckBox.isChecked = book.wishlist
            readCheckBox.isChecked = book.read

        }

        saveButton.setOnClickListener {
            val book = Book(
                if (isEditing) bookId else -1,
                title = titleEditText.text.toString(),
                author = authorEditText.text.toString(),
                genre = genreTextView.text.toString(),
                isbn = isbnEditText.text.toString(),
                publisher = publisherEditText.text.toString(),
                edition = editionEditText.text.toString(),
                pages = pagesEditText.text.toString(),
                year = yearEditText.text.toString(),
                price = priceEditText.text.toString(),
                rating = ratingBar.rating.toDouble(),
                read = readCheckBox.isChecked,
                wishlist = wishListCheckBox.isChecked

            )

            if (isEditing) {
                dbHelper.updateBook(book)
                Toast.makeText(this, "Book updated", Toast.LENGTH_SHORT).show()
                finish()

            } else {
                dbHelper.addBook(book)
                Toast.makeText(this, "Book added", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        backButton.setOnClickListener {
            finish()
        }

        genreTextView.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_genre_select, null)
            val genreDialogSpinner = dialogView.findViewById<Spinner>(R.id.dialogGenreSpinner)

            val adapter = ArrayAdapter.createFromResource(
                this,
                R.array.genre_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                genreDialogSpinner.adapter = adapter
            }

            // Find the index of the current genre in the Spinner's adapter
            val currentGenre = genreTextView.text.toString()
            val genreIndex = adapter.getPosition(currentGenre)

            // Set the selected item of the Spinner to the current genre
            genreDialogSpinner.setSelection(genreIndex)

            val genreDialog = AlertDialog.Builder(this)
                .setTitle("Select Genre")
                .setView(dialogView)
                .create()

            var isFirstSelection = true

            genreDialogSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        if (isFirstSelection) {
                            isFirstSelection = false
                        } else {
                            val genre = parent.getItemAtPosition(position).toString()
                            updateGenre(genre)
                            genreDialog.dismiss()
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // Do nothing
                    }
                }

            genreDialog.show()
        }
    }

    fun updateGenre(genre: String) {
        genreTextView.text = genre
    }
}
