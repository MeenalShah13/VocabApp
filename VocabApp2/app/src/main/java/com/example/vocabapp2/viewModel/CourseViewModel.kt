package com.example.vocabapp2.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vocabapp.model.Course
import com.example.vocabapp.model.WordDetails

class CourseViewModel : ViewModel() {
    private val _course = MutableLiveData<Course?> (null)

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