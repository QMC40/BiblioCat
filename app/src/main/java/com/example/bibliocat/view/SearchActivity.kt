package com.example.bibliocat.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.bibliocat.databinding.ActivitySearchBinding
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class SearchActivity : AppCompatActivity() {

    @JsonClass(generateAdapter = true)
    data class GoogleBooksResult(
        val items: List<GoogleBooksItem>
    )

    @JsonClass(generateAdapter = true)
    data class GoogleBooksItem(
        val volumeInfo: VolumeInfo
    )

    @JsonClass(generateAdapter = true)
    data class VolumeInfo(
        val title: String,
        val authors: List<String>,
        val publisher: String,
        val publishedDate: String,
        val description: String,
        @Json(name = "imageLinks") val imageLinks: ImageLinks
    )

    @JsonClass(generateAdapter = true)
    data class ImageLinks(
        @Json(name = "thumbnail") val thumbnail: String
    )

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchButton.setOnClickListener {
            val searchQuery = binding.bookTitleEditText.text.toString()
            searchBookInfo(searchQuery)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun searchBookInfo(bookTitle: String) {
        Log.d("SearchActivity", "Searching for book: $bookTitle")
        val client = OkHttpClient()
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(GoogleBooksResult::class.java)

        val request = Request.Builder()
            .url("https://www.googleapis.com/books/v1/volumes?q=$bookTitle&maxResults=1")
            .build()

        GlobalScope.launch(Dispatchers.IO) {
            try {
                client.newCall(request).execute().use { response ->
                    val body = response.body?.string()
                    if (body != null) {
                        val result = adapter.fromJson(body)
                        result?.items?.forEach { item: GoogleBooksItem ->
                            withContext(Dispatchers.Main) {
                                binding.titleTextView.text = "Title: ${item.volumeInfo.title}"
                                binding.authorsTextView.text =
                                    "Authors: ${item.volumeInfo.authors.joinToString(", ")}"
                                binding.publisherTextView.text =
                                    "Publisher: ${item.volumeInfo.publisher}"
                                binding.publishedDateTextView.text =
                                    "Published Date: ${item.volumeInfo.publishedDate}"
                                binding.descriptionTextView.text =
                                    "Description: ${item.volumeInfo.description}"
                                // Load the thumbnail into the ImageView using Glide
                                Glide.with(this@SearchActivity)
                                    .load(item.volumeInfo.imageLinks.thumbnail)
                                    .into(binding.thumbnailImageView)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("SearchActivity", "Error searching for book: $e")
            }
        }
    }
}