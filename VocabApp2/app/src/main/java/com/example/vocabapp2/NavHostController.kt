package com.example.vocabapp2

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.vocabapp2.model.BottomNavItem
import com.example.vocabapp2.viewModel.CourseViewModel
import com.example.vocabapp2.viewModel.MyWordsViewModel
import com.example.vocabapp2.viewModel.DictionaryViewModel
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
        composable(R.string.course_navigate_route.toString()) { CourseScreen(courseViewModel, myWordsViewModel, context, modifier) }
        composable(R.string.login_navigate_route.toString()) { LoginScreen(modifier)}
    }
}
