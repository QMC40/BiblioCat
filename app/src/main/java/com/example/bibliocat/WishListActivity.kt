package com.example.bibliocat

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WishlistActivity : AppCompatActivity() {

    private lateinit var adapter: BookAdapter
    private lateinit var dbHelper: BookDbHelper
    private lateinit var backBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)

        dbHelper = BookDbHelper(this)
        val wishlist = dbHelper.getWishlist()
        backBtn = findViewById(R.id.backBtn)

        val count = dbHelper.getWishListCount()
        val countTextView = findViewById<TextView>(R.id.wishlistBooksCounterTextView)
        countTextView.text = resources.getQuantityString(R.plurals.wishlist_count_format, count, count)
        val emptyView = findViewById<TextView>(R.id.emptyView)

        adapter = BookAdapter(wishlist)
        val recyclerView = findViewById<RecyclerView>(R.id.bookRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        if (wishlist.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
        }

        backBtn.setOnClickListener {
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        val wishlist = dbHelper.getWishlist()
        adapter.bookList = wishlist
        adapter.notifyDataSetChanged()
    }
}