package com.example.bibliocat

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BookDbHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS books")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "bookLibrary.db"
        private const val DATABASE_VERSION = 1
        private const val CREATE_TABLE = "CREATE TABLE books (" +
                "id INTEGER PRIMARY KEY," +
                "title TEXT," +
                "author TEXT," +
                "isbn TEXT," +
                "publisher TEXT," +
                "edition TEXT," +
                "pages TEXT," +
                "genre TEXT," +
                "year TEXT," +
                "price TEXT)"
    }

    fun getBooksCount(): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM books", null)
        val count = cursor.count
        cursor.close()
        return count
    }

    fun addBook(book: Book) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("title", book.title)
        values.put("author", book.author)
        values.put("isbn", book.isbn)
        values.put("publisher", book.publisher)
        values.put("edition", book.edition)
        values.put("pages", book.pages)
        values.put("genre", book.genre)
        values.put("year", book.year)
        values.put("price", book.price)
        db.insert("books", null, values)
        db.close()
    }

    fun getAllBooks(): List<Book> {
        val bookList = ArrayList<Book>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM books", null)
        if (cursor.moveToFirst()) {
            do {
                val book = Book(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("title")),
                    cursor.getString(cursor.getColumnIndexOrThrow("author")),
                    cursor.getString(cursor.getColumnIndexOrThrow("isbn")),
                    cursor.getString(cursor.getColumnIndexOrThrow("publisher")),
                    cursor.getString(cursor.getColumnIndexOrThrow("edition")),
                    cursor.getString(cursor.getColumnIndexOrThrow("pages")),
                    cursor.getString(cursor.getColumnIndexOrThrow("genre")),
                    cursor.getString(cursor.getColumnIndexOrThrow("year")),
                    cursor.getString(cursor.getColumnIndexOrThrow("price"))
                )
                bookList.add(book)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return bookList
    }
}