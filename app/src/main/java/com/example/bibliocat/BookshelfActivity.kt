package com.example.bibliocat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookshelfActivity : AppCompatActivity() {

    private lateinit var adapter: BookAdapter
    private lateinit var dbHelper: BookDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_shelf_activity)

        dbHelper = BookDbHelper(this)
        val bookList = dbHelper.getAllBooks()

        adapter = BookAdapter(bookList)
        val recyclerView = findViewById<RecyclerView>(R.id.bookRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        val bookList = dbHelper.getAllBooks()
        adapter.bookList = bookList
        adapter.notifyDataSetChanged()
    }
}
