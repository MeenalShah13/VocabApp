package com.example.vocabapp2.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabapp2.network.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DictionaryViewModel : ViewModel() {
    val wordMeaning = mutableStateOf("")
    val isLoading = mutableStateOf(false)

    fun fetchWordData(word: String) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = RetrofitInstance.apiService.getWordData(word)
                if (response.isSuccessful) {
                    val wordData = response.body()?.firstOrNull()
                    //wordMeaning.value = wordData?.meanings?.joinToString("\n") {
                        //"- ${it.partOfSpeech}: ${it.definitions.joinToString("; ") { def -> def.definition }}"
                    // Get the first meaning and definition only
                    val firstDefinition = wordData?.meanings?.firstOrNull()?.definitions?.firstOrNull()

// Update wordMeaning with the first definition
                    wordMeaning.value = firstDefinition?.let {
                        val meaningText = "- ${wordData.meanings.firstOrNull()?.partOfSpeech}: ${it.definition}"

                        // Fetch synonyms, if available
                        val synonymsText = it.synonyms?.takeIf { it.isNotEmpty() }
                            ?.joinToString(", ") ?: "No synonyms available"

                        "$meaningText\nSynonyms: $synonymsText"
                    } ?: "No response meaning found"
                } else {
                    wordMeaning.value = "$response No meaning found"
                }
            } catch (e: IOException) {
                wordMeaning.value = "Network error: ${e.message}"
            } catch (e: HttpException) {
                wordMeaning.value = "Server error: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }
}
