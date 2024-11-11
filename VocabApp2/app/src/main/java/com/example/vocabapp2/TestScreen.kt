package com.example.vocabapp2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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

                            Text(text = "Meaning: ${word.meaning}")
                            Spacer(modifier = Modifier.height(8.dp))

                            TextField(
                                value = userAnswer,
                                onValueChange = { userAnswer = it },
                                label = { Text("Enter the word") }
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            Button(onClick = {
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
                                enabled = userAnswer.isNotBlank() // Disable the button if no answer is entered
                            ) {
                                Text("Submit")
                            }
                        }
                    }
                }
            } else {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(modifier = modifier.padding(16.dp)
                        .height(100.dp)) {
                        Column(modifier = modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Your score: ${score}/${numberOfQuestions}",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Card(modifier = modifier.padding(16.dp).height(150.dp)) {
                        Column(modifier = modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Correct answers: ${correctAnswers.joinToString()}",
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Display incorrect answers with their meanings
                    if (incorrectAnswers.isNotEmpty()) {
                        Card(modifier = modifier.padding(16.dp).height(500.dp)) {
                            IncorrectWordsCard(incorrectAnswers, modifier)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = {restartTest()}) {
                        Text("Restart Test")
                    }
                }
            }
        }
    }
}

@Composable
fun IncorrectWordsCard(incorrectWords: List<WordDetails>, modifier: Modifier = Modifier) {
    Text("Incorrect answers:", fontSize = 16.sp, modifier = modifier.padding(8.dp))
    LazyColumn(modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        items(incorrectWords) { word ->
            Text("Word: ${word.synonyms[0]}\n Meaning: ${word.meaning}", fontSize = 16.sp)
        }
    }
}