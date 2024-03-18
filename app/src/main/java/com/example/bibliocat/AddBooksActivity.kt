package com.example.bibliocat

import android.view.View
import androidx.appcompat.app.AppCompatActivity

class AddBooksActivity : AppCompatActivity() {
fun enterBookManually(view: View) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(R.id.fragment_container, AddBookFragment())
    transaction.addToBackStack(null)
    transaction.commit()
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
