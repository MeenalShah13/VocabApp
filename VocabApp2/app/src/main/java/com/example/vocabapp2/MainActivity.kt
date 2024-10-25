package com.example.vocabapp2

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults.windowInsets
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.vocabapp2.model.BottomNavItem
import com.example.vocabapp2.ui.theme.VocabApp2Theme
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VocabApp2Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    VocabApp(modifier = Modifier)
                }
            }
        }
    }
}

@Composable
fun VocabApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val context: Context = LocalContext.current
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController, modifier = modifier) }
    ) { innerPadding ->
        NavHostContainer(navController, Modifier.padding(innerPadding), context)
    }
}



@Composable
fun BottomNavigationBar(navController: NavHostController, modifier: Modifier = Modifier) {
    val items = listOf(
        BottomNavItem.Courses,
        BottomNavItem.Dictionary,
        BottomNavItem.Test,
        BottomNavItem.My_Words
    )
    BottomAppBar (modifier = modifier) {
        val currentRoute = currentRoute(navController)
        Row(modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(windowInsets),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically) {
            items.forEach { item ->
                Button(onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }) {
                    Icon(item.icon, contentDescription = item.label)
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VocabApp2Theme {
    }
}