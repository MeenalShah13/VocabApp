package com.example.vocabapp2

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.vocabapp.model.Course
import com.example.vocabapp.model.MyWordsArray
import com.example.vocabapp.model.WordDetails
import com.example.vocabapp2.ui.theme.VocabApp2Theme
import com.example.vocabapp2.utils.loadDataFromJson
import com.example.vocabapp2.viewmodel.DictionaryViewModel
import kotlinx.coroutines.launch
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

@Composable
fun CoursesScreen(modifier: Modifier = Modifier, context: Context) {
    var courseList by remember { mutableStateOf<List<Course>>(emptyList()) }

    val coroutineScope = rememberCoroutineScope()

    // Load JSON data when the Composable is launched
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            courseList = listOf(loadDataFromJson<Course>(fileName = "course1.json", context = context),
                loadDataFromJson<Course>(fileName = "course2.json", context = context),
                loadDataFromJson<Course>(fileName = "course3.json", context = context),
                loadDataFromJson<Course>(fileName = "course4.json", context = context))
        }
    }

    if (courseList.isEmpty())  {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Loading...")
        }
    } else {
        LazyColumn(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(courseList) { course ->
                CourseCard(course.courseName, modifier)
            }
        }
    }
}

fun getSampleDataset(): List<WordDetails> {
    return listOf(
        WordDetails("apple", "A round fruit with red or green skin and a whitish interior"),
        WordDetails("banana", "A long curved fruit with a yellow skin"),
        WordDetails("car", "A road vehicle with an engine, four wheels, and seats"),
        WordDetails("dog", "A domesticated carnivorous mammal with a bark"),
        WordDetails("elephant", "A large mammal with a trunk and tusks"),
        WordDetails("giraffe", "A tall African mammal with a very long neck"),
        WordDetails("house", "A building for human habitation"),
        WordDetails("idea", "A thought or suggestion as to a possible course of action"),
        WordDetails("joker", "A person who makes jokes"),
        WordDetails("kite", "A light framework covered with paper or cloth"),
        WordDetails("lemon", "A small yellow fruit with a tart flavor")
    )
}

@Composable
fun TestScreen(context: Context, modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .imePadding()
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = modifier.padding(16.dp)
                .height(600.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Spacer(modifier.weight(1f))
            val dataset = getSampleDataset() // Get your sample dataset
            var wordsList = remember { mutableStateListOf<WordDetails>() }
            wordsList.addAll(dataset.shuffled().take(5)) // Take 5 random words

            // Track the current word index, user's answer, score, and whether the test is finished
            val currentWord = remember { mutableStateOf(0) }
            val userAnswer = remember { mutableStateOf("") }
            val score = remember { mutableStateOf(0) }
            val showScore = remember { mutableStateOf(false) }
            val correctAnswers = remember { mutableStateListOf<String>() }
            val incorrectAnswers = remember { mutableStateListOf<WordDetails>() }

            // Check if there are no words in the dataset
            if (dataset.isEmpty()) {
                Text("No words learned")
            } else {
                // Only show the question if the test is not finished
                if (currentWord.value < 5) {
                    val word = wordsList[currentWord.value]
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(text = "Meaning: ${word.meaning}")
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = userAnswer.value,
                            onValueChange = { userAnswer.value = it },
                            label = { Text("Enter the word") }
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        // Submit button action
                        Button(
                            onClick = {
                                // Check if the answer is correct
                                if (userAnswer.value.trim().equals(word.word, ignoreCase = true)) {
                                    score.value++
                                    correctAnswers.add(word.word)
                                } else {
                                    incorrectAnswers.add(word) // Store the incorrect word and meaning
                                }

                                // Move to the next word
                                userAnswer.value = "" // Reset answer
                                currentWord.value++

                                // If all words have been answered, show the score
                                if (currentWord.value >= 5) {
                                    showScore.value = true
                                }
                            },
                            enabled = userAnswer.value.isNotBlank() // Disable the button if no answer is entered
                        ) {
                            Text("Submit")
                        }
                    }
                }

                // Display score after all questions are answered
                if (showScore.value) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text("Your score: ${score.value}/5",
                                fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Correct answers: ${correctAnswers.joinToString()}", fontSize = 24.sp)
                        Spacer(modifier = Modifier.height(16.dp))

                        // Display incorrect answers with their meanings
                        if (incorrectAnswers.isNotEmpty()) {
                            Text("Incorrect answers:",fontSize = 24.sp)
                            incorrectAnswers.forEach { word ->
                                Text("Word: ${word.word} | Meaning: ${word.meaning}", fontSize = 24.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            // Reset the test
                            currentWord.value = 0
                            score.value = 0
                            correctAnswers.clear()
                            incorrectAnswers.clear() // Clear incorrect answers
                            wordsList.clear()
                            wordsList.addAll(dataset.shuffled().take(5)) // Reshuffle the words
                            showScore.value = false // Hide score screen
                        }) {
                            Text("Restart Test")
                        }
                    }
                }
            }
        }
    }
}


/*
@Composable
fun TestScreen(context: Context, modifier: Modifier = Modifier) {
    var answer by remember { mutableStateOf("") }
    Column(modifier = modifier
        .imePadding()
        .verticalScroll(rememberScrollState()),
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
}*/
/*
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
*/

@Composable
fun DictionaryScreen(
    modifier: Modifier = Modifier,
    viewModel: DictionaryViewModel
    ) {
    var searchText by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Enter word") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { viewModel.fetchWordData(searchText) }) {
            Text("Search")
        }

        Text(
            text = "You are searching for: ${searchText}",
            //modifier = modifier.padding(top = 16.dp)
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
                    text = "${searchText}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.padding(bottom = 8.dp)
                )

                Spacer(modifier = Modifier.height(1.dp))

                if (viewModel.isLoading.value) {
                    CircularProgressIndicator()
                } else {
                    Text(
                        text = viewModel.wordMeaning.value,
                        fontSize = 24.sp
                    )
                }
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
            wordDetails.synonyms?.let { word ->
                val synonyms = word.subList(1, word.size-1)
                // Heading
                Text(
                    text = word[0],
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

@Preview(showBackground = true)
@Composable
fun CoursesPreview() {
    VocabApp2Theme {
        Surface(modifier = Modifier.fillMaxSize()) {
            CoursesScreen(modifier = Modifier, context = LocalContext.current)
        }
    }
}
