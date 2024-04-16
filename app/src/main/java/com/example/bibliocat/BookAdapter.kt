package com.example.bibliocat

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookAdapter(var bookList: List<Book>) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val authorTextView: TextView = itemView.findViewById(R.id.authorTextView)
        val isbnTextView: TextView = itemView.findViewById(R.id.isbnTextView)
        val coverImageView: ImageView = itemView.findViewById(R.id.coverImageView)
        val deleteButton: View = itemView.findViewById(R.id.deleteButton)
        val editButton: View = itemView.findViewById(R.id.editButton)
        // Add more TextViews for other book details as needed
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_item, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.titleTextView.text = book.title
        holder.authorTextView.text = book.author
        holder.isbnTextView.text = book.isbn

        // Add more bindings for other book details as needed

        // Add logic to load cover image if available
//         Glide.with(holder.itemView.context)
//            .load(book.coverImage)
//            .placeholder(R.drawable.ic_book)
//            .error(R.drawable.ic_book)
//            .into(holder.coverImageView)

        holder.deleteButton.setOnClickListener {
            val dbHelper = BookDbHelper(holder.itemView.context)
            dbHelper.deleteBook(book.id)
            bookList = dbHelper.getBookshelf()
            notifyDataSetChanged()
        }

        holder.editButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, EditBookActivity::class.java)
            intent.putExtra("bookId", book.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = bookList.size
}