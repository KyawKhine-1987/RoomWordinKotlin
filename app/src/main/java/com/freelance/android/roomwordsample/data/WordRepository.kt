package com.freelance.android.roomwordsample.data

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import android.util.Log

/**
 * Created by KyawKhine on 03/04/2019 10:37 PM.
 */


class WordRepository(private val wordDao: WordDao) {

    private val LOG_TAG = WordRepository::class.java.name

    val allWords: LiveData<List<Word>> = wordDao.getAllWords()

    @WorkerThread
    suspend fun add(word: Word){
        Log.i(LOG_TAG, "TEST: add() called...")

        wordDao.add(word)
    }
}
