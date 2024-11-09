package com.example.vocabapp2.model

data class WordResponse(
    val word: String,
    val meanings: List<Meaning>
)

data class Meaning(
    val partOfSpeech: String,
    val definitions: List<Definition>
)

data class Definition(
    val definition: String,
    val synonyms: List<String>? = emptyList(),
)
