package com.example.bibliocat.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bibliocat.model.Book
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Book::class], version = 1)
abstract class MyCollectionDatabase : RoomDatabase() {

    abstract fun myCollectionDao() : MyCollectionDao

    companion object {

        @Volatile
        private var instance: MyCollectionDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDatabaseInstance(context: Context) : MyCollectionDatabase {

            synchronized(this) {

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