package com.example.vocabapp2

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vocabapp2.viewModel.CourseListViewModel

@Composable
fun CoursesListScreen(courseListViewModel: CourseListViewModel, navController: NavController, modifier: Modifier = Modifier) {

    val courseList = courseListViewModel.getCourseNames()

    if (courseList.isEmpty())  {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(stringResource(R.string.empty_course_data),
                style = MaterialTheme.typography.displayMedium)
        }
    } else {
        LazyColumn(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(courseList) { courseName ->
                CourseCard(courseName, courseListViewModel, navController, modifier)
            }
        }
    }
}

@Composable
fun CourseCard(courseName: String, viewModel: CourseListViewModel, navController: NavController, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(16.dp)
        .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp),
        onClick = {
            viewModel.setClickedCourse(courseName)
            navController.navigate("Course_Screen")
        }) {
        Spacer(modifier.height(20.dp))
        Row {
            Text(
                text = courseName,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall,
                modifier = modifier.weight(2f)
            )
        }
        Spacer(modifier.height(20.dp))
    }
}