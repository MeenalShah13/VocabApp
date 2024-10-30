package com.example.vocabapp.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import kotlinx.serialization.Serializable
import java.io.File

@Serializable
@JsonDeserialize
data class WordDetails(
    val id: Int? = null,
    val synonyms: List<String>,
    val meaning: String,
    val pronounciation: File? = null
)