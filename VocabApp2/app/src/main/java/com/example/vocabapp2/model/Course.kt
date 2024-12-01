package com.example.vocabapp.model

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
@JsonDeserialize
data class Course(
    val courseId: String = UUID.randomUUID().toString(),
    val courseName: String,
    var wordList: List<WordDetails> = emptyList()
)