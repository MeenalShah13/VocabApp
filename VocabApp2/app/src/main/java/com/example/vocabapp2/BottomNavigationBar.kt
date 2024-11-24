package com.example.vocabapp2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults.windowInsets
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.vocabapp2.model.BottomNavItem

@Composable
fun BottomNavigationBar(mainScreenNavController: NavHostController, modifier: Modifier = Modifier) {
    val items = listOf(
        BottomNavItem.Courses,
        BottomNavItem.Dictionary,
        BottomNavItem.Test,
        BottomNavItem.My_Words
    )
    BottomAppBar (modifier = modifier) {
        val currentRoute = currentRoute(mainScreenNavController)
        Row(modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(windowInsets),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically) {
            items.forEach { item ->
                Button(onClick = {
                    //Handling bug where pressing courses button after going to individual course does nothing
                    val route = mainScreenNavController.currentBackStackEntry?.destination?.route
                    if (route != null && route == "Course_Screen" && item.route == BottomNavItem.Courses.route) {
                        mainScreenNavController.popBackStack()
                    } else {
                        mainScreenNavController.navigate(item.route) {
                            popUpTo(mainScreenNavController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }) {
                    Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(item.icon, contentDescription = item.label)
                        Text(text = item.label, textAlign = TextAlign.Center, fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}