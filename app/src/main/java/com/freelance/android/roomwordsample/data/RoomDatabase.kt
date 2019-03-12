package com.freelance.android.roomwordsample.data

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.util.Log
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.IO
import kotlinx.coroutines.experimental.launch

/**
 * Created by KyawKhine on 03/04/2019 6:51 PM.
 */

@Database(entities = [Word::class], version = 1, exportSchema = false)
public abstract class WordRoomDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao  //Define the DAOs that work with the database. Provide an abstract "getter" method for each @Dao.

    companion object {
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null
        private val LOG_TAG = WordRoomDatabase::class.java.name

        fun getDatabase(context: Context,
                        scope: CoroutineScope
        ): WordRoomDatabase {
            Log.i(LOG_TAG, "TEST: getDatabase() called...")

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "Word_database"
                ).addCallback(WordDatabaseCallback(scope)).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            Log.i(LOG_TAG, "TEST: onOpen() called...")

            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.wordDao())
                }
            }
        }

        fun populateDatabase(wordDao: WordDao) {
            Log.i(LOG_TAG, "TEST: populateDatabase() called...")

            wordDao.deleteAll()

            var word = Word("Hello")
            wordDao.add(word)
            word = Word("World!")
            wordDao.add(word)
        }
    }


}