package com.example.vocabapp2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vocabapp2.viewModel.DictionaryViewModel
import com.example.vocabapp2.viewModel.MyWordsViewModel

@Composable
fun DictionaryScreen(
    modifier: Modifier = Modifier,
    dictionaryViewModel: DictionaryViewModel,
    myWordsViewModel: MyWordsViewModel
) {
    var searchText by remember { mutableStateOf("") }
    val searchDefinition by dictionaryViewModel.resultWord
    var processedSearchText = ""

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Center content vertically
    ) {
        // Search TextField
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Enter word") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Search Button
        Button(onClick = {
            processedSearchText = searchText.trim().lowercase()
            dictionaryViewModel.fetchWordData(word = processedSearchText, myWordsViewModel = myWordsViewModel)
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer, // Custom background color (light peach)
                contentColor = Color.Black // Custom text color
            )) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Display Results or Loading Indicator
        if (dictionaryViewModel.isLoading.value) {
            CircularProgressIndicator()
        } else {
            searchDefinition?.let { definition ->
                Card(
                    modifier = modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = modifier.padding(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = definition.synonyms.getOrNull(0) ?: "",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = modifier.padding(bottom = 8.dp)
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = definition.meaning,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}
