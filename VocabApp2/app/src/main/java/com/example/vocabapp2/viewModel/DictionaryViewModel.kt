package com.example.vocabapp2.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabapp.model.WordDetails
import com.example.vocabapp2.network.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DictionaryViewModel : ViewModel() {
    val isLoading = mutableStateOf(false)
    val wordList: MutableList<String> = mutableListOf()
    var resultWord = mutableStateOf<WordDetails?>(null)

    fun fetchWordData(word: String, myWordsViewModel: MyWordsViewModel) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = RetrofitInstance.apiService.getWordData(word)
                if (response.isSuccessful) {
                    val wordData = response.body()?.firstOrNull()
                    val firstDefinition = wordData?.meanings?.firstOrNull()?.definitions?.firstOrNull()?.definition
                    val firstSynonyms = wordData?.meanings?.firstOrNull()?.synonyms

                    wordList.add(word)
                    if (firstSynonyms?.isNotEmpty() == true) {
                        wordList.addAll(firstSynonyms)
                    }

                    resultWord.value = WordDetails(
                        id = null,
                        synonyms = wordList,
                        meaning = firstDefinition.orEmpty(),
                        pronounciation = null
                    )

                    myWordsViewModel.addWord(resultWord.value!!)
                } else {
                    Log.e("DictionaryViewModel", "Word: ${word} not found")
                }
            } catch (e: IOException) {
                Log.e("DictionaryViewModel", "Network error: ${e.message}")
            } catch (e: HttpException) {
                Log.e("DictionaryViewModel", "Server error: ${e.message}")
            } finally {
                isLoading.value = false
            }
        }
        wordList.clear()
    }
}