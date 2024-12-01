package com.example.vocabapp2.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson
import com.google.gson.JsonElement

inline fun <reified T> loadListFromJson(jsonString: String): List<T> {
    var gson = Gson()
    val list = gson.fromJson(jsonString, Array<JsonElement>::class.java)
    val result = emptyList<T>().toMutableList()
    for (item in list) {
        result.add(gson.fromJson(item, T::class.java))
    }
    return result
}

inline fun <reified T> loadSetFromJson(jsonString: String): Set<T> {
    var gson = Gson()
    val list = gson.fromJson(jsonString, Array<JsonElement>::class.java)
    val result = emptySet<T>().toMutableSet()
    for (item in list) {
        result.add(gson.fromJson(item, T::class.java))
    }
    return result
}

fun convertToJsonString(any: Any): String {
    val gson = Gson()
    return gson.toJson(any)
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

fun getCurrentUser(): FirebaseUser? {
    val auth = FirebaseAuth.getInstance()
    return auth.currentUser
}

fun getFirstName(fullName: String?): String {
    if (fullName.isNullOrBlank()) {
        return ""
    }
    val firstName = fullName.split(" ")[0]
    return firstName
}
