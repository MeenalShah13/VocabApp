package com.example.vocabapp2

import androidx.benchmark.perfetto.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vocabapp.model.WordDetails
import com.example.vocabapp2.viewModel.MyWordsViewModel
import kotlin.math.min

@Composable
fun TestScreen(myWordsViewModel: MyWordsViewModel, modifier: Modifier = Modifier) {
    val dataset = myWordsViewModel.getWordList()
    val numberOfQuestions = min(dataset.size, 10)
    val wordsList = remember { mutableStateListOf<WordDetails>() }
    wordsList.addAll(dataset.shuffled().take(numberOfQuestions))

    var showScore by remember { mutableStateOf(false) }
    var currentWordIndex by remember { mutableStateOf(0) }
    var userAnswer by remember { mutableStateOf("") }
    var score by remember { mutableStateOf(0) }
    val correctAnswers = remember { mutableStateListOf<String>() }
    val incorrectAnswers = remember { mutableStateListOf<WordDetails>() }
    var trimmedWord = ""

    val restartTest = {
        currentWordIndex = 0
        userAnswer = ""
        score = 0
        correctAnswers.clear()
        incorrectAnswers.clear()
        showScore = false
        wordsList.clear()
        wordsList.addAll(dataset.shuffled().take(min(dataset.size, numberOfQuestions))) // Reshuffle the words
    }

    Column(modifier = modifier
        .imePadding()
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        if (dataset.isEmpty()) {
            Text("No words learned")
        } else {
            if (!showScore) {
                Card(
                    modifier = modifier.padding(16.dp)
                        .height(600.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE4C4)),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Spacer(modifier.weight(1f))
                    // Only show the question if the test is not finished
                    if (currentWordIndex <= (numberOfQuestions - 1)) {
                        val word = wordsList[currentWordIndex]
                        Column(modifier = modifier
                            .fillMaxSize()
                            .padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally) {

                            // Question Section (Meaning of the word)
                            Text(
                                text = "Meaning: ${word.meaning}",
                                fontSize = 24.sp, // Increased text size for better visibility
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 16.dp) // Added padding to separate the question from the answer section
                            )

                            // Answer Section (Input Field)
                            TextField(
                                value = userAnswer,
                                onValueChange = { userAnswer = it },
                                label = { Text("Enter the word") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            // Submit Button with color and functionality
                            Button(
                                onClick = {
                                    trimmedWord = userAnswer.trim().lowercase()
                                    if (trimmedWord in word.synonyms) {
                                        score++
                                        correctAnswers.add(trimmedWord)
                                    } else {
                                        incorrectAnswers.add(word) // Store the incorrect word and meaning
                                    }

                                    // Move to the next word
                                    userAnswer = ""
                                    currentWordIndex++

                                    // If all words have been answered, show the score
                                    if (currentWordIndex > (numberOfQuestions - 1)) {
                                        showScore = true
                                    }
                                },
                                enabled = userAnswer.isNotBlank(), // Disable the button if no answer is entered
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xffff6347),
                                    contentColor = Color.White // White text
                                )
                            ) {
                                Text("Submit")
                            }
                        }
                    }
                }
            } else {
                // Display score and correct answers after completing the test
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(modifier = modifier.padding(16.dp)
                        .height(120.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE4C4))
                    )
                     {
                        Column(modifier = modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Your score: ${score}/${numberOfQuestions}",
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Correct Answers Section
                    if (correctAnswers.isNotEmpty()) {
                        Card(modifier = modifier
                            .padding(16.dp)
                            .height(150.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE4C4)),
                            elevation = CardDefaults.cardElevation(8.dp)
                        )
                            {
                            Column(
                                modifier = modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "Correct answers: ${correctAnswers.joinToString()}",
                                    fontSize = 23.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0B852B)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Display incorrect answers with their meanings
                    if (incorrectAnswers.isNotEmpty()) {
                        Card(modifier = modifier
                            .padding(16.dp)
                            .height(500.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE4C4)),
                            elevation = CardDefaults.cardElevation(8.dp)
                        ) {
                            IncorrectWordsCard(incorrectAnswers, modifier)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = { restartTest() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xffff6347),
                            contentColor = Color.White // White text
                        )) {
                        Text("Restart Test")
                    }
                }
            }
        }
    }
}

@Composable
fun IncorrectWordsCard(incorrectWords: List<WordDetails>, modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Text(
            "Incorrect Answers",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFB71C1C), // Red color for emphasis
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(incorrectWords) { word ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFDEDEC)) // Light red background
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        // Incorrect Word with Icon
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "❌ Word: ",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFB71C1C)
                            )
                            Text(
                                text = word.synonyms[0],
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFB71C1C)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Meaning
                        Text(
                            text = "Meaning: ${word.meaning}",
                            fontSize = 18.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
