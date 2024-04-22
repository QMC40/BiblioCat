package com.example.bibliocat.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bibliocat.databinding.BookItemBinding
import com.example.bibliocat.model.Book
import com.example.bibliocat.util.ConvertImage
import com.example.bibliocat.view.BookDetailsActivity

class BookAdapter(private val activity: Activity) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private var bookList: List<Book> = ArrayList()

    fun setBooks(books: List<Book>) {
        this.bookList = books
        notifyDataSetChanged()
    }

    class BookViewHolder(val bookBinding: BookItemBinding) : RecyclerView.ViewHolder(bookBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {

        val view = BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(view)
    }

    // Return the size of your dataset (invoked by the layout manager), required by RecyclerView.Adapter
    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {

        val book = bookList[position]
        with(holder) {

            bookBinding.titleTextView.text = book.title
            bookBinding.authorTextView.text = book.author
            val coverImageAsBitmap = ConvertImage.convertToBitmap(book.coverImageAsString)
            bookBinding.coverImageView.setImageBitmap(coverImageAsBitmap)

            bookBinding.cardView.setOnClickListener {
                val intent = Intent(activity, BookDetailsActivity::class.java)
                intent.putExtra("bookId", book.bookId)
                activity.startActivity(intent)
            }
        }
    }

    // Return the item at the given position, required by RecyclerView.Adapter
    fun returnItemAtGivenPosition(position: Int): Book {
        return bookList[position]
    }
}

