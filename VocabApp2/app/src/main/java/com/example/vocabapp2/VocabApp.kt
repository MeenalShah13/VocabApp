package com.example.vocabapp2

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.vocabapp2.viewModel.CourseListViewModel
import com.example.vocabapp2.viewModel.MyWordsViewModel
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun VocabApp(modifier: Modifier) {
    val navController = rememberNavController()
    val context: Context = LocalContext.current
    val firestoreDatabase: FirebaseFirestore = FirebaseFirestore.getInstance()
    val courseListViewModel: CourseListViewModel = viewModel()
    val myWordsViewModel: MyWordsViewModel = viewModel()

    NavHostContainer(firestoreDatabase, courseListViewModel, myWordsViewModel, navController, context, modifier)
}