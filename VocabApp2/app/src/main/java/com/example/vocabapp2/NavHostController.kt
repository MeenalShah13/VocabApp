package com.example.vocabapp2

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vocabapp2.model.BottomNavItem
import com.example.vocabapp2.viewModel.CourseViewModel
import com.example.vocabapp2.viewModel.DictionaryViewModel
import com.example.vocabapp2.viewModel.MyWordsViewModel
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun NavHostContainer(courseViewModel: CourseViewModel, myWordsViewModel: MyWordsViewModel, navController: NavHostController, firestoreDatabase: FirebaseFirestore, context: Context, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Courses.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Courses.route) { CoursesListScreen(courseViewModel, firestoreDatabase, navController, context, modifier) }
        composable(BottomNavItem.Test.route) { TestScreen(myWordsViewModel, modifier) }
        composable(BottomNavItem.Dictionary.route) { DictionaryScreen(modifier, DictionaryViewModel(), myWordsViewModel) }
        composable(BottomNavItem.My_Words.route) { MyWordsScreen(myWordsViewModel, modifier) }
        composable("CourseScreen") { CourseScreen(courseViewModel, myWordsViewModel, context, modifier) }
    }
}
