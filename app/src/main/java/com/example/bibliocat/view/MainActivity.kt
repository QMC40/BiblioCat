@file:Suppress("unused", "never used")

package com.example.bibliocat.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.bibliocat.R
import com.example.bibliocat.adapter.BookAdapter
import com.example.bibliocat.databinding.ActivityMainBinding
import com.example.bibliocat.viewmodel.BookViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    // set vars
    private lateinit var bookViewModel: BookViewModel
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var bookAdapter: BookAdapter

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        // set the bookViewModel for the activity
        bookViewModel = ViewModelProvider(this)[BookViewModel::class.java]

        // clear any text in the search field
        mainBinding.searchField.text.clear()

        // set the onClickListeners for the buttons
        mainBinding.searchBtn.setOnClickListener {
            val searchQuery = mainBinding.searchField.text.toString()
            if (searchQuery.isEmpty()) {
                mainBinding.searchField.error = "Please enter a search query"
                return@setOnClickListener
            } else {
                searchForBooks(searchQuery)
            }
        }

        mainBinding.bookshelfBtn.setOnClickListener {
            val intent = Intent(this, BookShelfActivity::class.java)
            intent.putExtra("button", "bookshelf")
            startActivity(intent)
        }

        mainBinding.wishlistBtn.setOnClickListener {
            val intent = Intent(this, BookShelfActivity::class.java)
            intent.putExtra("button", "wishlist")
            startActivity(intent)
        }

        mainBinding.whatAmIReadingBtn.setOnClickListener {
            val intent = Intent(this, BookShelfActivity::class.java)
            intent.putExtra("button", "booksread")
            startActivity(intent)
        }

        mainBinding.shareBtn.setOnClickListener {
            openShareActivity(it)
        }

        // get the book count in a coroutine and set the text view to the count
        lifecycleScope.launch {
            val count = bookViewModel.getTotalCount()
            mainBinding.bookCounterTextView.text =
                resources.getQuantityString(R.plurals.shelf_format, count, count)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onResume() {
        super.onResume()
        // Refresh the book count in a coroutine and set the text view to the count
        lifecycleScope.launch {
            val count = bookViewModel.getTotalCount()
            mainBinding.bookCounterTextView.text =
                resources.getQuantityString(R.plurals.shelf_format, count, count)
        }
        // Clear the search field
        mainBinding.searchField.text.clear()
    }

    fun openAddBooksActivity(view: View) {
        // Handle addBooksBtn click
        // Start the EditBookActivity in add mode
        val intent = Intent(this, EditBookActivity::class.java)
        intent.putExtra("bookId", -1)
        startActivity(intent)
    }

    private fun openBookShelfActivity(view: View) {
        // Handle bookshelfBtn click
        // Start the BookShelfActivity with the bookshelf button
        val intent = Intent(this, BookShelfActivity::class.java)
        intent.putExtra("button", "bookshelf")
        startActivity(intent)
    }

    private fun openShareActivity(view: View) {
        // Handle shareBtn click
//        val intent = Intent(this, ShareActivity::class.java)
//        startActivity(intent)

        // make a toast to show that the activity is not implemented yet
        // and will be implemented in the future
        val toast = Toast.makeText(applicationContext, "This activity is not implemented yet",
            Toast.LENGTH_SHORT).show()
    }

    private fun openWishlistActivity(view: View) {
        // Handle wishlistBtn click
        val intent = Intent(this, BookShelfActivity::class.java)
        intent.putExtra("button", "wishlist")
        startActivity(intent)
    }

    private fun openBooksReadActivity(view: View) {
        // Handle whatAmIReadingBtn click
        val intent = Intent(this, BookShelfActivity::class.java)
        intent.putExtra("button", "booksread")
        startActivity(intent)
    }

    private fun searchForBooks(searchQuery: String) {
        // Handle searchBtn click
        val intent = Intent(this, BookShelfActivity::class.java)
        // Send the search query to the BookShelfActivity as an extra as well as
        // tagging the intent with the button that was clicked to get to the search
        // display items
        intent.putExtra("button", "search")
        intent.putExtra("searchQuery", searchQuery)
        startActivity(intent)
    }
}
