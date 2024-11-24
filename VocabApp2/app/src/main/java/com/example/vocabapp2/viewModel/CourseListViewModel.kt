package com.example.vocabapp2.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vocabapp.model.Course
import com.example.vocabapp.model.WordDetails
import com.example.vocabapp2.utils.loadListFromJson
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow

class CourseListViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val _courseList = MutableStateFlow<List<Course?>>(emptyList())
    private val _course = MutableLiveData<String>("")

    init {
        loadCourseList()
    }

    private fun addCourse(course: Course) {
        _courseList.value = _courseList.value + course
    }

    fun getCourseNames(): List<String> {
        return _courseList.value.mapNotNull { it?.courseName }
    }

    fun setClickedCourse(courseName: String) {
        _course.value = courseName
    }

    fun getClickedCourse(): String {
        return _course.value.toString()
    }

    fun clearClickedCourse() {
        _course.value = ""
    }

    fun getCourse(courseName: String): Course? {
        return _courseList.value.find { it?.courseName == courseName }
    }

    fun loadCourseList() {
        firestore.collection("courses").get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                if (!queryDocumentSnapshots.isEmpty) {
                    val coursesListFromCloud = queryDocumentSnapshots.documents
                    for (course in coursesListFromCloud) {
                        val c = Course(course.id, course.get("courseName").toString())
                        c.wordList = loadListFromJson<WordDetails>(course.get("wordList").toString())
                        addCourse(c)
                    }
                }
            }
    }
}