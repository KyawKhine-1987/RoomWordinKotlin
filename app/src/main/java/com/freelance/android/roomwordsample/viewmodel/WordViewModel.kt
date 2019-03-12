package com.freelance.android.roomwordsample.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.util.Log
import com.freelance.android.roomwordsample.data.Word
import com.freelance.android.roomwordsample.data.WordRepository
import com.freelance.android.roomwordsample.data.WordRoomDatabase
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import kotlin.coroutines.experimental.CoroutineContext


/**
 * Created by KyawKhine on 03/04/2019 10:56 PM.
 */


class WordViewModel(application: Application) : AndroidViewModel(application) {

    private val LOG_TAG = WordViewModel::class.java.name
    private val repository: WordRepository
    val allWords: LiveData<List<Word>>
    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    init {
        val wordsDao = WordRoomDatabase.getDatabase(application, scope).wordDao()
        repository = WordRepository(wordsDao)
        allWords = repository.allWords
    }

    override fun onCleared() {
        Log.i(LOG_TAG, "TEST: onCleared() called...")

        super.onCleared()
        parentJob.cancel()
    }

    fun add(word: Word) = scope.launch(Dispatchers.IO) {
        Log.i(LOG_TAG, "TEST: add() called...")

        repository.add(word)
    }
}