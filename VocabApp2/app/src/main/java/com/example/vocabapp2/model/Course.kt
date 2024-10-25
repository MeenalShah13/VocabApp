package com.example.vocabapp.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import kotlinx.serialization.Serializable

@Serializable
@JsonDeserialize
data class Course (
    val courseName: String,
    var wordList: List<WordDetails>
    )