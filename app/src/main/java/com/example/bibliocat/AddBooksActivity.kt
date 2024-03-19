@file:Suppress("declaration", "unused")

package com.example.bibliocat

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class AddBooksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_books_activity)
    }


    fun enterBookManually(view: View) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, AddBookFragment())
        transaction.addToBackStack(null)
        transaction.commit()
        print("Entering book manually")
    }

    fun scanCover(view: View) {
        print("Scanning cover")
    }

    fun searchOnline(view: View) {
        print("Searching online")
    }

    fun scanBarcode(view: View) {
        print("Scanning barcode")
    }
}
