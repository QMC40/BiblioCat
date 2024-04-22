@file:Suppress("declaration", "unused")

package com.example.bibliocat.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.bibliocat.R
import com.example.bibliocat.databinding.ActivityAddBookBinding
import com.example.bibliocat.util.CameraFragment
import com.example.bibliocat.viewmodel.BookViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddBooksActivity : AppCompatActivity() {

    private lateinit var bookViewModel: BookViewModel
    private lateinit var addBookBinding: ActivityAddBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBookBinding = ActivityAddBookBinding.inflate(layoutInflater)
        setContentView(addBookBinding.root)

        bookViewModel = BookViewModel(application)

        lifecycleScope.launch {

            // get total count in the collection
            val count = bookViewModel.getTotalCount()
            addBookBinding.bookCounterTextView.text =
                resources.getQuantityString(R.plurals.shelf_format, count, count)

        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh the book count
        GlobalScope.launch {
            val count = bookViewModel.getTotalCount()
            addBookBinding.bookCounterTextView.text =
                resources.getQuantityString(R.plurals.shelf_format, count, count)
        }
    }

    fun enterBookManually(view: View) {
        // Start the EditBookActivity in add mode
        val intent = Intent(this, EditBookActivity::class.java)
        intent.putExtra("bookId", -1)
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
