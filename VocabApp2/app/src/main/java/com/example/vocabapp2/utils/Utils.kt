package com.example.vocabapp2.utils

import com.example.vocabapp2.R
import com.google.gson.Gson
import com.google.gson.JsonElement

inline fun <reified T> loadListFromJson(jsonString: String): List<T> {
    var gson = Gson()
    val list = gson.fromJson(jsonString, Array<JsonElement>::class.java)
    var result = emptyList<T>()
    for (item in list) {
        result += gson.fromJson(item, T::class.java)
    }
    return result
}

fun getSynonyms(synonyms: List<String>): String {
    if (synonyms.size == 1) {
        return ""
    }

    var synonymsString = "Synonyms: "
    for (index in intArrayOf(1,(synonyms.size - 1))) {
        synonymsString += synonyms[index]
        if (index != synonyms.size - 1) {
            synonymsString += ", "
        }
    }
    return synonymsString
}