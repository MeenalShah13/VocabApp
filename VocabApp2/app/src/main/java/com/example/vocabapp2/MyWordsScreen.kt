package com.example.vocabapp2

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vocabapp.model.WordDetails

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
fun MyWordsCard(wordDetails: WordDetails, context: Context, modifier: Modifier = Modifier) {
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
            wordDetails.synonyms.let { words ->
                val synonyms = words.subList(1, words.size-1)
                // Heading
                Text(
                    text = words[0],
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.padding(bottom = 8.dp)
                )
                // Normal text
                Text(
                    text = wordDetails.meaning,
                    fontSize = 16.sp
                )
                // Normal text
                Text(
                    text = "Synonyms : " + synonyms.reduce { acc, s -> "$acc, $s" },
                    fontSize = 16.sp
                )
            }
        }
    }
}