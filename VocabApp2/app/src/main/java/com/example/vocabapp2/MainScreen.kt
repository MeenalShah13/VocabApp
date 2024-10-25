package com.example.vocabapp2

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vocabapp2.ui.theme.VocabApp2Theme

@Composable
fun CoursesScreen(modifier: Modifier = Modifier) {
    val courseList: List<String> = listOf("Course 1", "Course 2")
    LazyColumn(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {
        items(courseList) {course ->
            CourseCard(course, modifier)
        }
    }
}

@Composable
fun TestScreen(context: Context, modifier: Modifier = Modifier) {
    var answer by remember { mutableStateOf("") }
    Column(modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Card(modifier = modifier.padding(16.dp)
            .height(200.dp)
            .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp)) {
            Spacer(modifier.weight(1f))
            Text(text = "evoking a keen sense of sadness or regret.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 24.sp)
            Spacer(modifier.weight(1f))
        }
        Spacer(modifier = modifier.height(50.dp))
        OutlinedTextField(value = answer,
            onValueChange = { answer = it },
            singleLine = true,
            label = { Text(text = "Answer") })
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            if (answer == "poignant") {
                Toast.makeText(context, "You are Correct!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "You are Wrong :(", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "Submit")
        }

    }
}

@Composable
fun DictionaryScreen(modifier: Modifier = Modifier) {
    // State to hold the search query
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    Column(modifier = modifier.padding(16.dp)) {
        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search") },
            singleLine = true
        )
        // Display the current search query below the search bar
        Text(
            text = "You are searching for: ${searchQuery.text}",
//            modifier = modifier.padding(top = 16.dp)
        )
        Card(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
//            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            // Layout the content inside the card
            Column(
                modifier = modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Heading
                Text(
                    text = "Conscience",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.padding(bottom = 8.dp)
                )
                // Normal text
                Text(
                    text = "An inner feeling or voice viewed as acting as a guide to the rightness or wrongness of one's behavior.",
                    fontSize = 16.sp
                )
                // Normal text
                Text(
                    text = "Synonyms : Censor, Compunction, Demur, Duty, Morals, Principles, Qualms, Scruples, Superego",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun MyWordsScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Card(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            // Layout the content inside the card
            Column(
                modifier = modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Heading
                Text(
                    text = "Conscience",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.padding(bottom = 8.dp)
                )
                // Normal text
                Text(
                    text = "An inner feeling or voice viewed as acting as a guide to the rightness or wrongness of one's behavior.",
                    fontSize = 16.sp
                )
                // Normal text
                Text(
                    text = "Synonyms : Censor, Compunction, Demur, Duty, Morals, Principles, Qualms, Scruples, Superego",
                    fontSize = 16.sp
                )
            }
        }

        Card(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            // Layout the content inside the card
            Column(
                modifier = modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Heading
                Text(
                    text = "Curriculum",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.padding(bottom = 8.dp)
                )
                // Normal text
                Text(
                    text = "The subjects comprising a course of study in a school or college.",
                    fontSize = 16.sp
                )
                // Normal text
                Text(
                    text = "Synonyms : Syllabus, Course of study/studies, Program of study/studies, Educational program, Subjects, Modules, Timetable, Schedule",
                    fontSize = 16.sp
                )
            }
        }
    }
}


@Composable
fun CourseCard(courseName: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier
        .padding(16.dp)
        .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)) {
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

@Preview(showBackground = true)
@Composable
fun CoursesPreview() {
    VocabApp2Theme {
        Surface(modifier = Modifier.fillMaxSize()) {
            CoursesScreen(modifier = Modifier)
        }
    }
}
