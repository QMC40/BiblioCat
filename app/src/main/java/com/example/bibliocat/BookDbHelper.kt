package com.example.bibliocat

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class BookDbHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
        db.execSQL(CREATE_WISHLIST_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS books")
        db.execSQL("DROP TABLE IF EXISTS wishlist")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "bookLibrary.db"
        private const val DATABASE_VERSION = 5
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
                "price TEXT," +
                "wishlist BOOLEAN DEFAULT 0 CHECK(wishlist IN (0,1)))"

        private const val CREATE_WISHLIST_TABLE = "CREATE TABLE wishlist (" +
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

    fun getWishListCount(): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM wishlist",null)
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
        values.put("wishlist", book.wishlist)
        db.insert("books", null, values)
        db.close()
    }

    fun addToWishList(book: Book) {
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
        db.insert("wishlist", null, values)
        Log.d("Wishlist","got here ")
        db.close()
    }

    fun deleteBook(id: Int) {
        val db = this.writableDatabase
        db.delete("books", "id = ?", arrayOf(id.toString()))
        db.close()
    }

    fun deleteFromWishlist(bookId: Int) {
        val db = this.writableDatabase
        db.delete("wishlist", "id = ?", arrayOf(bookId.toString()))
        db.close()
    }

    fun getAllBooks(): List<Book> {
        val bookList = ArrayList<Book>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM books WHERE wishlist = 0", null)
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

    fun getAllWishlistBooks(): List<Book> {
        val wishList = ArrayList<Book>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM books WHERE wishlist = 1", null)
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
                wishList.add(book)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return wishList
    }

    fun getBook(bookId: Int): Book {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM books WHERE id = ?", arrayOf(bookId.toString()))
        cursor?.moveToFirst()
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
        cursor.close()
        return book

    }

    fun getWishlistBook(bookId: Int): Book {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM wishlist WHERE id = ?", arrayOf(bookId.toString()))
        cursor?.moveToFirst()
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
        cursor.close()
        return book

    }

    fun updateBook(updatedBook: Book) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("title", updatedBook.title)
        values.put("author", updatedBook.author)
        values.put("isbn", updatedBook.isbn)
        values.put("publisher", updatedBook.publisher)
        values.put("edition", updatedBook.edition)
        values.put("pages", updatedBook.pages)
        values.put("genre", updatedBook.genre)
        values.put("year", updatedBook.year)
        values.put("price", updatedBook.price)
        db.update("books", values, "id = ?", arrayOf(updatedBook.id.toString()))
        db.close()

    }

    fun updateWishlist(id: Int, checked: Boolean) {

    }
}