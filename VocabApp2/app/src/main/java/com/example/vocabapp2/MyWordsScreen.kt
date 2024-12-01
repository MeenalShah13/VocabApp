package com.example.vocabapp2

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vocabapp.model.WordDetails
import com.example.vocabapp2.utils.getSynonyms
import com.example.vocabapp2.viewModel.MyWordsViewModel


@Composable
fun MyWordsScreen(myWordsViewModel: MyWordsViewModel, modifier: Modifier = Modifier) {
    val wordList = myWordsViewModel.getWordList().toList()

    if (wordList.isNotEmpty()) {
        LazyColumn(modifier = modifier.padding(16.dp)) {
            items(wordList) { wordInfo ->
                MyWordsCard(wordInfo, modifier)
            }
        }
    } else {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxSize()) {
            Text(modifier = modifier.padding(16.dp),
                text = stringResource(R.string.no_words),
                textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun MyWordsCard(wordInfo: WordDetails, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .animateContentSize( // Smooth animation when expanding or contracting
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (expanded) Color(0xfffaa191) else Color(0xFFFFE4C4) // Orange when expanded, light gray otherwise
        )
    ) {
        Column(
            modifier = modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = wordInfo.synonyms[0],
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.padding(bottom = 8.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = { expanded = !expanded }, // Toggle dropdown
                    modifier = modifier
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            }

            if (expanded) {
                // Display the expanded content
                Text(
                    text = wordInfo.meaning,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = getSynonyms(wordInfo.synonyms),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}


