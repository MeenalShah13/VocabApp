package com.example.vocabapp2

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController

@Composable
fun VocabApp(modifier: Modifier) {
    val navController = rememberNavController()
    val context: Context = LocalContext.current
    NavHostContainer(navController, context, modifier)
}