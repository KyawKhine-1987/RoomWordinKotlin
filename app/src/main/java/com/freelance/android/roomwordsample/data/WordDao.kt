package com.freelance.android.roomwordsample.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

/**
 * Created by KyawKhine on 03/04/2019 5:37 PM.
 */

@Dao
interface WordDao {

    @Query("Select * From word_table Order By word ASC;")
    fun getAllWords(): LiveData<List<Word>>

    @Insert
    fun add(word: Word)

    @Query("Delete From word_table;")
    fun deleteAll()
}