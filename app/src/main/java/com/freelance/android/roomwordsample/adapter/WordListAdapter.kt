package com.freelance.android.roomwordsample.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.freelance.android.roomwordsample.R
import com.freelance.android.roomwordsample.activities.NewWordActivity
import com.freelance.android.roomwordsample.data.Word

/**
 * Created by KyawKhine on 03/07/2019 2:48 PM.
 */


class WordListAdapter internal constructor(
    context: Context) : RecyclerView.Adapter<WordListAdapter.WordViewHolder>(){

    private val LOG_TAG = NewWordActivity::class.java.name
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var words = emptyList<Word>() // Cached copy of words

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordListAdapter.WordViewHolder {
        Log.i(LOG_TAG, "TEST: onCreateViewHolder() called...")

        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun getItemCount() = words.size

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        Log.i(LOG_TAG, "TEST: onBindViewHolder() called...")

        val current = words[position]
        holder.wordItemView.text = current.word
    }

    internal fun setWords(words: List<Word>) {
        Log.i(LOG_TAG, "TEST: setWords() called...")

        this.words = words
        notifyDataSetChanged()
    }

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
    }
}