package com.example.vocabapp2

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.vocabapp2.model.BottomNavItem
import com.example.vocabapp2.viewModel.CourseViewModel
import com.example.vocabapp2.viewModel.MyWordsViewModel
import com.example.vocabapp2.utils.getCurrentUser
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun VocabApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val context: Context = LocalContext.current
    var firestoreDatabase: FirebaseFirestore = FirebaseFirestore.getInstance()
    val courseViewModel: CourseViewModel = viewModel()
    val myWordsViewModel: MyWordsViewModel = viewModel()

    Scaffold(
        topBar = { TopTitleBar(context, modifier) },
        bottomBar = { BottomNavigationBar(navController = navController, modifier = modifier) },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Surface(modifier = modifier.fillMaxSize()
            .padding(innerPadding)
            .statusBarsPadding()
            .safeDrawingPadding()) {
            NavHostContainer(courseViewModel, myWordsViewModel, navController, firestoreDatabase, context, modifier)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopTitleBar(context: Context, modifier: Modifier = Modifier) {
    val user: FirebaseUser? = getCurrentUser()
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier.weight(4f))
                Text(
                    text = "VocabApp",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier.weight(1f))
                Row(
                    modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (user != null) {
                        AsyncImage(model = ImageRequest.Builder(context)
                            .data(user.photoUrl)
                            .crossfade(true)
                            .build(),
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                                .clip(CircleShape))
                        Spacer(modifier.width(5.dp))
                        Text(text = getFirstName(user.displayName),
                            style = MaterialTheme.typography.labelMedium)
                    }
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

fun getFirstName(fullName: String?): String {
    if (fullName.isNullOrBlank()) {
        return ""
    }
    val firstName = fullName.split(" ")[0]
    return firstName
}