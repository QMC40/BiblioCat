package com.example.bibliocat

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.BitmapFactory

class BookDb(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

//        db.execSQL("DROP TABLE IF EXISTS wishlist")
//        db.execSQL("ALTER TABLE books RENAME COLUMN coverUrl TO coverResourceId")
//        db.execSQL("ALTER TABLE books RENAME COLUMN coverUrl TO coverResourceId")
//        db.execSQL("DROP TABLE IF EXISTS books")
//        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "bookLibrary.db"
        private const val DATABASE_VERSION = 11

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
                "coverImage BLOB DEFAULT NULL)"
    }

    fun getBooksCount(): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM books WHERE wishlist = 0", null)
        val count = cursor.count
        cursor.close()
        return count
    }

    fun getTotalCount(): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM books", null)
        val count = cursor.count
        cursor.close()
        return count
    }

    fun getWishListCount(): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM books WHERE wishlist = 1", null)
        val count = cursor.count
        cursor.close()
        return count
    }

    fun getReadBooksCount(): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM books WHERE read = 1", null)
        val count = cursor.count
        cursor.close()
        return count
    }

    fun addBook(book: Book) {
        if (book.title.isNullOrEmpty() && book.author.isNullOrEmpty()) {
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
//        values.put("coverImage", book.coverImage)

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
        val cursor = db.rawQuery("SELECT * FROM books WHERE wishlist = 0 ORDER BY title ASC", null)
        if (cursor.moveToFirst()) {
            do {

                val coverImageByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow("coverImage"))

                val coverImage = if (coverImageByteArray != null) {
                    BitmapFactory.decodeByteArray(coverImageByteArray, 0, coverImageByteArray.size)
                } else {
                    null
                }

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
                    cursor.getInt(cursor.getColumnIndexOrThrow("wishlist")) == 1,
                    cursor.getDouble(cursor.getColumnIndexOrThrow("rating")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("read")) == 1
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
        val cursor = db.rawQuery("SELECT * FROM books WHERE wishlist = 1 ORDER BY title ASC", null)
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
                    cursor.getInt(cursor.getColumnIndexOrThrow("wishlist")) == 1,
                    cursor.getDouble(cursor.getColumnIndexOrThrow("rating")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("read")) == 1
//                    cursor.getInt(cursor.getColumnIndexOrThrow("coverResourceId"))
                )
                wishList.add(book)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return wishList
    }

    fun getReadBooks(): List<Book> {
        val readBooks = ArrayList<Book>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM books WHERE read = 1 ORDER BY title ASC", null)
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
                    cursor.getInt(cursor.getColumnIndexOrThrow("wishlist")) == 1,
                    cursor.getDouble(cursor.getColumnIndexOrThrow("rating")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("read")) == 1
                )
                readBooks.add(book)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return readBooks
    }

    fun getBook(bookId: Int): Book {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM books WHERE id = ?", arrayOf(bookId.toString()))
        cursor?.moveToFirst()

        val coverImageByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow("coverImage"))

        val coverImage = if (coverImageByteArray != null) {
            BitmapFactory.decodeByteArray(coverImageByteArray, 0, coverImageByteArray.size)
        } else {
            null
        }

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
            cursor.getInt(cursor.getColumnIndexOrThrow("wishlist")) == 1,
            cursor.getDouble(cursor.getColumnIndexOrThrow("rating")),
            cursor.getInt(cursor.getColumnIndexOrThrow("read")) == 1,
//            coverImage = coverImage

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
//        values.put("coverResourceId", updatedBook.coverResourceId)


        db.update("books", values, "id = ?", arrayOf(updatedBook.id.toString()))
        db.close()

    }

    private fun searchBooks(query: String): List<Book>? {
        val foundBooks = ArrayList<Book>()
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR isbn LIKE ? OR publisher " +
                    "LIKE ? OR edition LIKE ? OR pages LIKE ? OR genre LIKE ? OR year LIKE ? OR price LIKE ? ORDER BY title ASC",
            arrayOf(
                "%$query%",
                "%$query%",
                "%$query%",
                "%$query%",
                "%$query%",
                "%$query%",
                "%$query%",
                "%$query%",
                "%$query%"
            )
        )
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
                    cursor.getInt(cursor.getColumnIndexOrThrow("wishlist")) == 1,
                    cursor.getDouble(cursor.getColumnIndexOrThrow("rating")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("read")) == 1
                )
                foundBooks.add(book)
            } while (cursor.moveToNext())
            cursor.close()
            return foundBooks
        } else {
            // If no match is found, return null
            return null
        }
    }

    fun getSearchResults(searchQuery: String): List<Book> {
        // Search for books in the database that match the search query
        val books = searchBooks(searchQuery)
        return books ?: emptyList()
    }
}