package com.example.vocabapp.model

import kotlinx.serialization.Serializable

@Serializable
data class MyWordsArray(
    var wordList: MutableSet<WordDetails>
)