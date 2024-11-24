package com.example.vocabapp2

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vocabapp2.viewModel.CourseViewModel
import com.example.vocabapp2.viewModel.MyWordsViewModel
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun NavHostContainer(firestoreDatabase: FirebaseFirestore, courseViewModel: CourseViewModel, myWordsViewModel: MyWordsViewModel, navController: NavHostController, context: Context, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "LoginScreen",
        modifier = modifier
    ) {
        composable("LoginScreen") { LoginScreen(navController, modifier) }
        composable("MainScreen") { MainScreen(firestoreDatabase, courseViewModel, myWordsViewModel, navController, context, modifier) }
    }
}