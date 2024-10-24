package com.example.vocabapp2.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomNavItem(val label: String, val icon: ImageVector, val route: String) {
    object Home : BottomNavItem("Home", Icons.Filled.Home, "home")
    object Categories : BottomNavItem("Categories", Icons.AutoMirrored.Filled.List, "Categories")
    object Favorites : BottomNavItem("Favorites", Icons.Filled.Favorite, "favorites")
    object Profile : BottomNavItem("Profile", Icons.Filled.Done, "profile")
}