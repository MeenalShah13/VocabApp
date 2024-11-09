package com.example.vocabapp2.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.example.vocabapp.model.WordDetails

class MyWordsViewModel: ViewModel() {
    private val _myWords = MutableLiveData<Set<WordDetails>>()
    val myWords: LiveData<Set<WordDetails>> = _myWords.asFlow().asLiveData()

    fun addWord(word: WordDetails) {
        _myWords.value = _myWords.value?.plus(word) ?: setOf(word)
    }

    fun sizeOfWordList(): Int {
        return _myWords.value?.size ?: 0
    }

    fun getWordList(): Set<WordDetails> {
        return _myWords.value ?: setOf()
    }
}