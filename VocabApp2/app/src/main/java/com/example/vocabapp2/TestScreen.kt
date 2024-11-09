package com.example.vocabapp2

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
}