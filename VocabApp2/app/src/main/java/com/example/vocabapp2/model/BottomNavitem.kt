package com.example.vocabapp2.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.filled.Book
//import androidx.compose.material.icons.filled.Dictionary
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.List


sealed class BottomNavItem(val label: String, val icon: ImageVector, val route: String, val title: String) {
    object Courses : BottomNavItem("Courses", Icons.Filled.Book, "CoursesList", title = "Courses")
    object Dictionary : BottomNavItem("Dictionary", Icons.Filled.Info, "Dictionary", title = "Dictionary")
    object Test : BottomNavItem("Test", Icons.Filled.Quiz, "Test", title = "Test")
    object My_Words : BottomNavItem("My Words", Icons.Filled.List, "My_Words", title = "My Words")
}