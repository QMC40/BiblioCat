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

/*
 Adapter class for the RecyclerView in the various activities to display the books.
    The adapter class is responsible for creating the view holder and binding the data to the view holder.
    The adapter class extends RecyclerView.Adapter and overrides the required methods.
    The adapter class also has a method to return the item at a given position.
 */

class BookAdapter(private val activity: Activity) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    // List variable to store the relevant books
    private var bookList: List<Book> = ArrayList()

    // Set the books in the adapter, required by RecyclerView.Adapter
    fun setBooks(books: List<Book>) {
        this.bookList = books
        notifyDataSetChanged()
    }

    // Inner class for the view holder, required by RecyclerView.Adapter to display the books in the
    // RecyclerView
    class BookViewHolder(val bookBinding: BookItemBinding) : RecyclerView.ViewHolder(bookBinding.root)

    // Create new views (invoked by the layout manager), required by RecyclerView.Adapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {

        // Create a new view, inflate it and return the view holder object
        val view = BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(view)
    }

    // Return the size of your dataset (invoked by the layout manager), required by RecyclerView.Adapter
    override fun getItemCount(): Int {
        return bookList.size
    }

    // Replace the contents of a view (invoked by the layout manager), required by RecyclerView.Adapter
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {

        // Get the book at the given position and bind the data to the view holder displaying only
        // the title, author and cover image of the book
        val book = bookList[position]
        with(holder) {

            bookBinding.titleTextView.text = book.title
            bookBinding.authorTextView.text = book.author

            // Convert the cover image from a string to a bitmap and set the image view
            val coverImageAsBitmap = ConvertImage.convertToBitmap(book.coverImageAsString)
            bookBinding.coverImageView.setImageBitmap(coverImageAsBitmap)

            // Set an on click listener for the card view to open the BookDetailsActivity to allow
            // the user to scroll through the books and click on a book to view its details
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

