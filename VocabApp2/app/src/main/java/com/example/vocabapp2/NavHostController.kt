package com.example.vocabapp2

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vocabapp2.model.BottomNavItem
import com.example.vocabapp2.model.CourseViewModel
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun NavHostContainer(courseViewModel: CourseViewModel, navController: NavHostController, firestoreDatabase: FirebaseFirestore, context: Context, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Courses.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Courses.route) { CoursesListScreen(courseViewModel, firestoreDatabase, navController, modifier, context) }
        composable(BottomNavItem.Test.route) { TestScreen(context, modifier) }
        composable(BottomNavItem.Dictionary.route) { DictionaryScreen(modifier) }
        composable(BottomNavItem.My_Words.route) { MyWordsScreen(modifier) }
        composable(R.string.course_navigate_route.toString()) { CourseScreen(courseViewModel,context, modifier) }
    }
}