package com.example.vocabapp2

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavHostContainer(navController: NavHostController, context: Context, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "LoginScreen",
        modifier = modifier
    ) {
        composable("LoginScreen") { LoginScreen(navController, modifier) }
        composable("MainScreen") { MainScreen(navController, context, modifier) }
    }
}