package com.example.vocabapp2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    Scaffold {innerPadding ->
        Text(text = "Home Screen", modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun Dictionary_screeen() {
    // State to hold the search query
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    Column(modifier = Modifier.padding(16.dp)) {
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
            modifier = Modifier.padding(top = 16.dp)
        )
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            // Layout the content inside the card
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Heading
                Text(
                    text = "Conscience",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
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
fun Favorites_screen() {
    Scaffold {innerPadding ->
        Text(text = "Favorites Screen", modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun ProfileScreen() {
   Column(modifier = Modifier.padding(16.dp)) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            // Layout the content inside the card
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Heading
                Text(
                    text = "Conscience",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
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
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            // Layout the content inside the card
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Heading
                Text(
                    text = "Curriculum",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
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
