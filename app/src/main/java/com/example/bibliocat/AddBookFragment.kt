package com.example.bibliocat

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Spinner
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment

class AddBookFragment : Fragment() {

    private lateinit var dbHelper: BookDbHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_book, container, false)

        dbHelper = BookDbHelper(context)

        val titleEditText = view.findViewById<EditText>(R.id.titleEditText)
        val authorEditText = view.findViewById<EditText>(R.id.authorEditText)
        val genreSpinner: Spinner = view.findViewById(R.id.genreSpinner)
        val isbnEditText = view.findViewById<EditText>(R.id.isbnEditText)
        val publisherEditText = view.findViewById<EditText>(R.id.publisherEditText)
        val editionEditText = view.findViewById<EditText>(R.id.editionEditText)
        val pagesEditText = view.findViewById<EditText>(R.id.pagesEditText)
        val yearEditText = view.findViewById<EditText>(R.id.yearEditText)
        val priceEditText = view.findViewById<EditText>(R.id.priceEditText)
        val wishlistCheckBox = view.findViewById<CheckBox>(R.id.wishlistSwitch)
        val readCheckBox = view.findViewById<CheckBox>(R.id.readSwitch)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
        val coverImageView = view.findViewById<ImageView>(R.id.coverImageView)
        val addButton = view.findViewById<Button>(R.id.addButton)

        // Create an ArrayAdapter using the string array and customized spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.genre_array,
            R.layout.spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            // Apply the adapter to the spinner
            genreSpinner.adapter = adapter
        }

        addButton.setOnClickListener {
            if (titleEditText.text.isEmpty() && authorEditText.text.isEmpty()) {
                Toast.makeText(context, "Title and Author cannot be empty", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            // Get the cover image as a bitmap
            val coverImage = coverImageView.drawable.toBitmap()
            // convert the bitmap to a byte array

            // Create a new book object to hold the data from the form
            val book = Book(
                title = titleEditText.text.toString(),
                author = authorEditText.text.toString(),
                isbn = isbnEditText.text.toString(),
                publisher = publisherEditText.text.toString(),
                edition = editionEditText.text.toString(),
                pages = pagesEditText.text.toString(),
                genre = genreSpinner.selectedItem.toString(),
                year = yearEditText.text.toString(),
                price = priceEditText.text.toString(),
                rating = ratingBar.rating.toDouble(),
                read = readCheckBox.isChecked,
                wishlist = wishlistCheckBox.isChecked,
//                coverImage = null
            )
            dbHelper.addBook(book)
            Toast.makeText(context, "Book has been added to the collection", Toast.LENGTH_SHORT)
                .show()

            // Update the counter in the AddBooksActivity
            (activity as AddBooksActivity).updateCounter()

            // Hide the keyboard
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)

            requireActivity().supportFragmentManager.popBackStack()
        }

        return view
    }

}