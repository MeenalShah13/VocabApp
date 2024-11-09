package com.example.vocabapp2.network

import com.example.vocabapp2.model.WordResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DictionaryApiService {
    @GET("en/{word}")
    abstract suspend fun getWordData(
        @Path("word") word: String
    ): Response<List<WordResponse>>
}