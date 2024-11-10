package com.example.vocabapp2.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabapp.model.WordDetails
import com.example.vocabapp2.utils.convertToJsonString
import com.example.vocabapp2.utils.getCurrentUser
import com.example.vocabapp2.utils.loadSetFromJson
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyWordsViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val _myWords = MutableStateFlow<Set<WordDetails>>(emptySet())
    val myWords: StateFlow<Set<WordDetails>> = _myWords.asStateFlow()

    init {
        loadMyWordsList()
        startPeriodicCloudUpdate()
    }

    fun addWord(word: WordDetails) {
        _myWords.value = _myWords.value + word
    }

    fun sizeOfWordList(): Int {
        return _myWords.value.size
    }

    fun getWordList(): Set<WordDetails> {
        return _myWords.value
    }

    fun saveWordsToCloud() {
        val currentWords = _myWords.value
        updateWordsToCloud(currentWords)
    }

    private fun startPeriodicCloudUpdate() {
        viewModelScope.launch {
            while (true) {
                delay( 10 * 1000) // 10-second delay

                val currentWords = _myWords.value
                updateWordsToCloud(currentWords)
            }
        }
    }

    private fun updateWordsToCloud(words: Set<WordDetails>) {
        val userId = getCurrentUser()?.uid ?: return
        val wordsJsonString = convertToJsonString(words)

        firestore.collection("users")
            .document(userId)
            .set(mapOf("myWordsList" to wordsJsonString))
            .addOnSuccessListener {
                Log.d("MyWordsViewModel", "MyWords Updated for user: $userId")
            }
            .addOnFailureListener { error ->
                Log.e("MyWordsViewModel", "Failed to Update Words in Cloud: \n" + error.message.toString())
            }
    }

    fun loadMyWordsList() {
        val userId = getCurrentUser()?.uid ?: return
        val userDocRef = firestore.collection("users").document(userId).addSnapshotListener {
            snapshot, error ->
            if (error != null) {
                Log.e("MyWordsViewModel", "Failed to Update Words in Cloud: \n" + error.message.toString())
            }
            if (snapshot != null && snapshot.exists()) {
                _myWords.value = loadSetFromJson<WordDetails>(snapshot.get("myWordsList").toString())
            }
        }
    }
}