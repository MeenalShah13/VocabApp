package com.example.vocabapp.model

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import kotlinx.serialization.Serializable
import java.io.File

@Serializable
@JsonDeserialize
data class WordDetails(
    val word: String = null.toString(),
    val meaning: String = null.toString(),
    val synonyms: List<String>? = null,
    val pronounciation: File? = null,
    val id: Int? = null,
)