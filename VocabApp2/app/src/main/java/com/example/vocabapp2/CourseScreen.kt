package com.example.vocabapp2

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vocabapp.model.WordDetails
import com.example.vocabapp2.model.CourseViewModel
import com.example.vocabapp2.utils.getSynonyms

@Composable
fun CourseScreen(courseViewModel: CourseViewModel, context: Context, modifier: Modifier = Modifier) {
    val courseWordList = courseViewModel.getWordList()
    var counter by remember { mutableStateOf(0) }

    if (courseWordList != null && courseWordList.isNotEmpty()) {
        val sizeOfList = courseWordList.size - 1

        Column(modifier = modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            courseWordList.get(counter).let { wordInfo ->
                Spacer(modifier = modifier.weight(1f))
                WordCard(wordInfo = wordInfo, modifier = modifier)
                Spacer(modifier = modifier.weight(1f))
                Buttons(counter = counter,
                    sizeOfList = sizeOfList,
                    onLeftClick = {counter--},
                    onRightClick = {counter++},
                    modifier = modifier)
                Spacer(modifier = modifier.weight(1f))
            }
        }
    }
}

@Composable
fun WordCard(wordInfo: WordDetails, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Spacer(modifier = modifier.height(20.dp))
        Column(
            modifier = modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = wordInfo.synonyms[0],
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(bottom = 8.dp)
            )
            Text(
                text = wordInfo.meaning,
                fontSize = 16.sp
            )
            Text(
                text = getSynonyms(wordInfo.synonyms),
                fontSize = 16.sp
            )
        }
        Spacer(modifier = modifier.height(20.dp))
    }
}

@Composable
fun Buttons(counter: Int, sizeOfList: Int, onLeftClick: () -> Unit, onRightClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(16.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
        if (counter != 0) {
            Button(onClick = onLeftClick) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous")
            }
        }
        Spacer(modifier = modifier.weight(1f))
        if (counter != sizeOfList) {
            Button(onClick = onRightClick) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next")
            }
        }
    }
}

