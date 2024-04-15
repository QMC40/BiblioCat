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

        db.execSQL("ALTER TABLE books ADD COLUMN " +
                "rating INTEGER DEFAULT 0 CHECK(rating BETWEEN 0 AND 5)")
        db.execSQL("ALTER TABLE books ADD COLUMN " +
                "read BOOLEAN DEFAULT 0 CHECK(read IN (0,1))")
        db.execSQL("ALTER TABLE books ADD COLUMN coverUrl TEXT")
//        db.execSQL("DROP TABLE IF EXISTS books")
//        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "bookLibrary.db"
        private const val DATABASE_VERSION = 7
        // Add more columns as needed
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
            "rating INTEGER DEFAULT 0 CHECK(rating BETWEEN 0 AND 5)," +
            "read BOOLEAN DEFAULT 0 CHECK(read IN (0,1))," +
            "wishlist BOOLEAN DEFAULT 0 CHECK(wishlist IN (0,1))," +
            "coverUrl TEXT" +
            ")"
    }

    fun getBooksCount(): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM books WHERE wishlist = 0", null)
        val count = cursor.count
        cursor.close()
        return count
    }

    fun getWishListCount(): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM books WHERE wishlist = 1",null)
        val count = cursor.count
        cursor.close()
        return count
    }

   fun addBook(book: Book) {
    if (book.title.isEmpty() && book.author.isEmpty()) {
        throw IllegalArgumentException("Title and author cannot be empty")
    }

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
    values.put("rating", book.rating)
    values.put("read", book.read)
    values.put("wishlist", book.wishlist)
    values.put("coverUrl", book.coverUrl)
    db.insert("books", null, values)
    db.close()
}

    fun deleteBook(id: Int) {
        val db = this.writableDatabase
        db.delete("books", "id = ?", arrayOf(id.toString()))
        db.close()
    }

    fun getBookshelf(): List<Book> {
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
                    cursor.getString(cursor.getColumnIndexOrThrow("price")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("rating")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("read")) == 1,
                    cursor.getInt(cursor.getColumnIndexOrThrow("wishlist")) == 1,
                    cursor.getString(cursor.getColumnIndexOrThrow("coverUrl"))
                )
                bookList.add(book)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return bookList
    }

    fun getWishlist(): List<Book> {
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
                    cursor.getString(cursor.getColumnIndexOrThrow("price")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("rating")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("read")) == 1,
                    cursor.getInt(cursor.getColumnIndexOrThrow("wishlist")) == 1,
                    cursor.getString(cursor.getColumnIndexOrThrow("coverUrl"))
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
            cursor.getString(cursor.getColumnIndexOrThrow("price")),
            cursor.getDouble(cursor.getColumnIndexOrThrow("rating")),
            cursor.getInt(cursor.getColumnIndexOrThrow("read")) == 1,
            cursor.getInt(cursor.getColumnIndexOrThrow("wishlist")) == 1,
            cursor.getString(cursor.getColumnIndexOrThrow("coverUrl"))

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
        values.put("rating", updatedBook.rating)
        values.put("read", updatedBook.read)
        values.put("wishlist", updatedBook.wishlist)
        values.put("coverUrl", updatedBook.coverUrl)
        db.update("books", values, "id = ?", arrayOf(updatedBook.id.toString()))
        db.close()

    }
}