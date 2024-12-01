package com.example.vocabapp2

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.vocabapp2.model.BottomNavItem

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedRoute = currentBackStackEntry?.destination?.route

    NavigationBar {
        val items = listOf(
            BottomNavItem.Courses,
            BottomNavItem.Test,
            BottomNavItem.Dictionary,
            BottomNavItem.My_Words
        )

        items.forEach { item ->
            val isSelected = item.route == selectedRoute
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = isSelected,
                onClick = {
                    //Handling bug where pressing courses button after going to individual course does nothing
                    val route = navController.currentBackStackEntry?.destination?.route
                    if (route != null && route == "CourseScreen" && item.route == BottomNavItem.Courses.route) {
                        navController.popBackStack()
                    } else {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFFFA726), // Highlighted color for selected page
                    selectedTextColor = Color(0xFFFFA726),
                    unselectedIconColor = Color.Gray, // Default color for unselected items
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}