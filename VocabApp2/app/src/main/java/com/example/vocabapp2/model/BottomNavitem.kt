package com.example.vocabapp2.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomNavItem(val label: String, val icon: ImageVector, val route: String) {
    object Courses : BottomNavItem("Course", Icons.Filled.Home, "Course")
    object Dictionary : BottomNavItem("Dictionary", Icons.AutoMirrored.Filled.List, "Dictionary")
    object Test : BottomNavItem("Favorites", Icons.Filled.Info, "Test")
    object My_Words : BottomNavItem("My Words", Icons.Filled.Done, "My_Words")
}