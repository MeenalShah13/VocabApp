package com.example.vocabapp2

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vocabapp2.model.BottomNavItem

@Composable
fun NavHostContainer(navController: NavHostController, modifier: Modifier = Modifier, context: Context) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Courses.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Courses.route) { CoursesScreen(modifier) }
        composable(BottomNavItem.Test.route) { TestScreen(context, modifier) }
        composable(BottomNavItem.Dictionary.route) { DictionaryScreen(modifier) }
        composable(BottomNavItem.My_Words.route) { MyWordsScreen(modifier) }
    }
}