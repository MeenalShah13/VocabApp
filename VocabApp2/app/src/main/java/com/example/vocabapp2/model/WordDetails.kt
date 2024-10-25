package com.example.vocabapp.model

import java.io.File

data class WordDetails(
    val id: Int,
    val wordList: List<String>,
    val meaning: String,
    val pronounciation: File
)