package com.example.bibliocat

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchResultsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var backBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        val books: ArrayList<Book> = intent.getParcelableArrayListExtra("books") ?: ArrayList()
        backBtn = findViewById(R.id.backBtn)

        viewManager = LinearLayoutManager(this)
        viewAdapter = BookAdapter(books)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        backBtn.setOnClickListener {
            finish()
        }
    }
}