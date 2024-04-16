package com.example.bibliocat

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BooksReadActivity : AppCompatActivity() {

    private lateinit var adapter: BookAdapter
    private lateinit var dbHelper: BookDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.books_read_activity)

        dbHelper = BookDbHelper(this)
        val bookList = dbHelper.getReadBooks()
        val emptyView = findViewById<TextView>(R.id.emptyView)

        val count = dbHelper.getReadBooksCount()
        val countTextView = findViewById<TextView>(R.id.booksReadCounterTextView)
        countTextView.text = resources.getQuantityString(R.plurals.book_read_count_format, count, count)

        adapter = BookAdapter(bookList)
        val recyclerView = findViewById<RecyclerView>(R.id.bookRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        if (bookList.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        val bookList = dbHelper.getReadBooks()
        adapter.bookList = bookList
        adapter.notifyDataSetChanged()
    }
}
