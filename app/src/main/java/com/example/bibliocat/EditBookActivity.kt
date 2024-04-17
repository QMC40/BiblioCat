package com.example.bibliocat

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class EditBookActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var authorEditText: EditText
    private lateinit var genreSpinner: Spinner
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_book)

        titleEditText = findViewById(R.id.titleEditText)
        authorEditText = findViewById(R.id.authorEditText)
        genreSpinner = findViewById(R.id.genreSpinner)
        isbnEditText = findViewById(R.id.isbnEditText)
        publisherEditText = findViewById(R.id.publisherEditText)
        editionEditText = findViewById(R.id.editionEditText)
        pagesEditText = findViewById(R.id.pagesEditText)
        yearEditText = findViewById(R.id.yearEditText)
        priceEditText = findViewById(R.id.priceEditText)
        ratingBar = findViewById(R.id.ratingBar)
        wishListCheckBox = findViewById(R.id.wishlistSwitch)
        readCheckBox = findViewById(R.id.readSwitch)
        saveButton = findViewById(R.id.updateButton)

        val bookId = intent.getIntExtra("bookId", -1)
        if (bookId == -1) {
            Toast.makeText(this, "Error: No book with that id in DB", Toast.LENGTH_SHORT).show()
            return
        }
        val dbHelper = BookDbHelper(this)
        val book = dbHelper.getBook(bookId)

        // Create an ArrayAdapter using the string array and customized spinner layout
        ArrayAdapter.createFromResource(
            this, // Use 'this' instead of 'requireContext()'
            R.array.genre_array,
            R.layout.spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            // Apply the adapter to the spinner
            genreSpinner.adapter = adapter
        }

        titleEditText.setText(book.title)
        authorEditText.setText(book.author)
        book.genre?.let { genreSpinner.setSelection(it) }
        isbnEditText.setText(book.isbn)
        publisherEditText.setText(book.publisher)
        editionEditText.setText(book.edition)
        pagesEditText.setText(book.pages.toString())
        yearEditText.setText(book.year.toString())
        priceEditText.setText(book.price.toString())
        ratingBar.rating = book.rating.toFloat()
        wishListCheckBox.isChecked = book.wishlist
        readCheckBox.isChecked = book.read

        saveButton.setOnClickListener {
            val updatedBook = Book(
                id = book.id,
                title = titleEditText.text.toString(),
                author = authorEditText.text.toString(),
                genre = genreSpinner.selectedItem.toString(),
                isbn = isbnEditText.text.toString(),
                publisher = publisherEditText.text.toString(),
                edition = editionEditText.text.toString(),
                pages = pagesEditText.text.toString(),
                year = yearEditText.text.toString(),
                price = priceEditText.text.toString(),
                rating = ratingBar.rating.toDouble(),
                read = readCheckBox.isChecked,
                wishlist = wishListCheckBox.isChecked


                // Add other fields as needed
            )
            dbHelper.updateBook(updatedBook)
            Toast.makeText(this, "Book updated", Toast.LENGTH_SHORT).show()
            finish()
        }
        // TODO: make a confirmation dialog before updating the book showing the user the changes
    }
}

private fun Spinner.setSelection(genre: String) {

}
