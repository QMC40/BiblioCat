package com.example.bibliocat
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class EditBookActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var authorEditText: EditText
    private lateinit var isbnEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_book)

        titleEditText = findViewById(R.id.titleEditText)
        authorEditText = findViewById(R.id.authorEditText)
        isbnEditText = findViewById(R.id.isbnEditText)
        saveButton = findViewById(R.id.saveButton)

        val bookId = intent.getIntExtra("bookId", -1)
        if(bookId == -1) {
            Toast.makeText(this, "Error: No book with that id in DB", Toast.LENGTH_SHORT).show()
            return
        }
        val dbHelper = BookDbHelper(this)
        val book = dbHelper.getBook(bookId)

        titleEditText.setText(book.title)
        authorEditText.setText(book.author)
        isbnEditText.setText(book.isbn)

        saveButton.setOnClickListener {
            val updatedBook = Book(
                id = book.id,
                title = titleEditText.text.toString(),
                author = authorEditText.text.toString(),
                isbn = isbnEditText.text.toString(),

                // Add other fields as needed
            )
            dbHelper.updateBook(updatedBook)
            Toast.makeText(this, "Book updated", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}