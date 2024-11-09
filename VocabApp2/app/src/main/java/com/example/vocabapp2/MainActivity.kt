package com.example.vocabapp2

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults.windowInsets
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.vocabapp2.model.BottomNavItem
import com.example.vocabapp2.ui.theme.VocabApp2Theme
import androidx.navigation.compose.rememberNavController
import com.example.vocabapp2.model.CourseViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
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
    var firestoreDatabase: FirebaseFirestore = FirebaseFirestore.getInstance()
    val courseViewModel: CourseViewModel = viewModel()

    Scaffold(
        topBar = { TopTitleBar(modifier) },
        bottomBar = { BottomNavigationBar(navController = navController, modifier = modifier) },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Surface(modifier = modifier.fillMaxSize()
            .padding(innerPadding)
            .statusBarsPadding()
            .safeDrawingPadding()) {
            NavHostContainer(courseViewModel, navController, firestoreDatabase, context, modifier)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopTitleBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier.width(130.dp))
                Text(
                    text = "VocabApp",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium
                )
                Row(
                    modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier.weight(1f))
                    Icon(Icons.Filled.Person, contentDescription = "Profile Picture")
                    Text(
                        text = "User",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        },
        modifier = modifier
    )
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VocabApp2Theme {
        Surface(modifier = Modifier.fillMaxSize()) {
            VocabApp(modifier = Modifier)
        }
    }
}