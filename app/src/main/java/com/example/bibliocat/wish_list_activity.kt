package com.example.bibliocat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WishlistActivity : AppCompatActivity() {

    private lateinit var adapter: BookAdapter
    private lateinit var dbHelper: BookDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)

        dbHelper = BookDbHelper(this)
        val wishlist = dbHelper.getWishlist()

        adapter = BookAdapter(wishlist)
        val recyclerView = findViewById<RecyclerView>(R.id.bookRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        }

    override fun onResume() {
        super.onResume()
        val wishlist = dbHelper.getWishlist()
        adapter.bookList = wishlist
        adapter.notifyDataSetChanged()
    }
}