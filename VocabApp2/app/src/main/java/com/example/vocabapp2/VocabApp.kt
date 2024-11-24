package com.example.vocabapp2

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.vocabapp2.viewModel.CourseViewModel
import com.example.vocabapp2.viewModel.MyWordsViewModel
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun VocabApp(modifier: Modifier) {
    val navController = rememberNavController()
    val context: Context = LocalContext.current
    val firestoreDatabase: FirebaseFirestore = FirebaseFirestore.getInstance()
    val courseViewModel: CourseViewModel = viewModel()
    val myWordsViewModel: MyWordsViewModel = viewModel()

    NavHostContainer(firestoreDatabase, courseViewModel, myWordsViewModel, navController, context, modifier)
}