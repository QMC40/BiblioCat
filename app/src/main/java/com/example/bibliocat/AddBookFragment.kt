package com.example.bibliocat

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.Fragment

class AddBookFragment : Fragment() {

    private lateinit var dbHelper: BookDbHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_book, container, false)

        dbHelper = BookDbHelper(context)

        val titleEditText = view.findViewById<EditText>(R.id.titleEditText)
        val authorEditText = view.findViewById<EditText>(R.id.authorEditText)
        val isbnEditText = view.findViewById<EditText>(R.id.isbnEditText)
        val publisherEditText = view.findViewById<EditText>(R.id.publisherEditText)
        val editionEditText = view.findViewById<EditText>(R.id.editionEditText)
        val pagesEditText = view.findViewById<EditText>(R.id.pagesEditText)
        val genreEditText = view.findViewById<EditText>(R.id.genreEditText)
        val yearEditText = view.findViewById<EditText>(R.id.yearEditText)
        val priceEditText = view.findViewById<EditText>(R.id.priceEditText)
        val wishlistCheckBox = view.findViewById<CheckBox>(R.id.wishlistSwitch)
        val readCheckBox = view.findViewById<CheckBox>(R.id.readSwitch)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
        val addButton = view.findViewById<Button>(R.id.addButton)


        addButton.setOnClickListener {
            if(titleEditText.text.isEmpty() && authorEditText.text.isEmpty()) {
                Toast.makeText(context, "Title and Author cannot be empty", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            val book = Book(
                title = titleEditText.text.toString(),
                author = authorEditText.text.toString(),
                isbn = isbnEditText.text.toString(),
                publisher = publisherEditText.text.toString(),
                edition = editionEditText.text.toString(),
                pages = pagesEditText.text.toString(),
                genre = genreEditText.text.toString(),
                year = yearEditText.text.toString(),
                price = priceEditText.text.toString(),
                rating = ratingBar.rating.toDouble(),
                read = readCheckBox.isChecked,
                wishlist = wishlistCheckBox.isChecked,
                coverUrl = ""
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