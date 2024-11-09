package com.example.vocabapp2.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import com.example.vocabapp.model.Course
import androidx.lifecycle.asLiveData
import com.example.vocabapp.model.WordDetails

class CourseViewModel : ViewModel() {
    private val _course = MutableLiveData<Course?> (null)
    val course: LiveData<Course?> = _course.asFlow().asLiveData()

    fun setCourse(course: Course) {
        _course.value = course
    }

    fun getCourseName(): String? {
        return _course.value?.courseName
    }

    fun getWordList(): List<WordDetails>? {
        return _course.value?.wordList
    }
}