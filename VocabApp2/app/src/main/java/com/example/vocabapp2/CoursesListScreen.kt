package com.example.vocabapp2

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vocabapp.model.Course
import com.example.vocabapp.model.WordDetails
import com.example.vocabapp2.utils.loadListFromJson
import com.example.vocabapp2.viewModel.CourseViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@Composable
fun CoursesListScreen(
    courseViewModel: CourseViewModel,
    firestoreDatabase: FirebaseFirestore,
    navController: NavController,
    context: Context,
    modifier: Modifier = Modifier
) {
    var courseList by remember { mutableStateOf<List<Course>>(emptyList()) }
    var textToDisplayId: Int by remember { mutableStateOf(R.string.loading) }
    var selectedCourseId by remember { mutableStateOf<String?>(null) } // To track the selected course

    val coroutineScope = rememberCoroutineScope()

    // Load JSON data when the Composable is launched
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            firestoreDatabase.collection("courses").get()
                .addOnSuccessListener { queryDocumentSnapshots ->
                    if (!queryDocumentSnapshots.isEmpty) {
                        val coursesListFromCloud = queryDocumentSnapshots.documents
                        for (course in coursesListFromCloud) {
                            val c = Course(course.id, course.get("courseName").toString())
                            c.wordList = loadListFromJson<WordDetails>(course.get("wordList").toString())
                            courseList += c
                        }
                    } else {
                        textToDisplayId = R.string.empty_course_data
                    }
                }
        }
    }

    if (courseList.isEmpty()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(textToDisplayId),
                style = MaterialTheme.typography.displayMedium
            )
        }
    } else {
        LazyColumn(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(courseList) { course ->
                CourseCard(
                    course = course,
                    viewModel = courseViewModel,
                    navController = navController,
                    context = context,
                    isSelected = selectedCourseId == course.courseId,
                    onCourseClick = { selectedCourseId = course.courseId }, // Update selected course
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun CourseCard(
    course: Course,
    viewModel: CourseViewModel,
    navController: NavController,
    context: Context,
    isSelected: Boolean,
    onCourseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cardBackgroundColor = if (isSelected) Color(0xffff6347) else Color(0xFFFFE4C4)
    val cardElevation = if (isSelected) 16.dp else 8.dp

    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
        elevation = CardDefaults.cardElevation(cardElevation),
        onClick = {
            onCourseClick()
            viewModel.setCourse(course)
            navController.navigate("CourseScreen")
        }
    ) {
        Spacer(modifier.height(20.dp))
        Row {
            Text(
                text = course.courseName,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall,
                modifier = modifier.weight(2f)
            )
        }
        Spacer(modifier.height(20.dp))
    }
}
