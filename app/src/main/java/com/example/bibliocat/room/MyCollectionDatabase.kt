package com.example.bibliocat.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bibliocat.model.Book
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

// Database class for the MyCollection database, which extends the RoomDatabase class.  The database
// is implemented as a Room database, which is an abstraction layer over SQLite.  The database
// contains a single table, Book, which is defined by the Book class. The database version is 1.
@Database(entities = [Book::class], version = 1)
abstract class MyCollectionDatabase : RoomDatabase() {

    // Abstract function to get the Data Access Object (DAO) for the Book table, this is an interface
    // that defines the database operations that can be performed on the Book table.
    abstract fun myCollectionDao() : MyCollectionDao

    // Companion object to get the database instance using the singleton pattern
    companion object {

        // Volatile instance of the database object to make it visible to all threads
        @Volatile
        private var instance: MyCollectionDatabase? = null

        // Function to get the database instance using the singleton pattern, this function is
        // synchronized to ensure that only one instance of the database is created.
        @OptIn(InternalCoroutinesApi::class)
        fun getDatabaseInstance(context: Context) : MyCollectionDatabase {synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyCollectionDatabase::class.java,
                        "my_collection"
                    ).build()
                }
                return instance as MyCollectionDatabase
            }
        }
    }
}