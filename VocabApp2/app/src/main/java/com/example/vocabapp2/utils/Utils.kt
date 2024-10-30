package com.example.vocabapp2.utils

import android.content.Context
import com.google.gson.Gson
import java.io.BufferedReader
import java.util.LinkedList

inline fun <reified T> loadDataFromJson(fileName: String, context: Context): T {
    var gson = Gson()
    val bufferedReader: BufferedReader = context.assets.open(fileName).bufferedReader()
    val inputString = bufferedReader.use { it.readText() }
    return gson.fromJson(inputString, T::class.java)
}