package com.example.vocabapp2

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    val firestoreDatabase: FirebaseFirestore = FirebaseFirestore.getInstance()
    val courseViewModel: CourseViewModel = viewModel()
    val myWordsViewModel: MyWordsViewModel = viewModel()
    Scaffold(
        topBar = { TopTitleBar(context, modifier) },
        bottomBar = { BottomNavigationBar(navController = navController) },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .statusBarsPadding()
                .safeDrawingPadding()
        ) {
            NavHostContainer(
                courseViewModel,
                myWordsViewModel,
                navController,
                firestoreDatabase,
                context,
                modifier
            )
        }
    }
}

@Composable
fun TopTitleBar(context: Context, modifier: Modifier = Modifier) {
    val user: FirebaseUser? = getCurrentUser()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "VocabApp",
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start
        )
        if (user != null && !user.displayName.isNullOrBlank()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(user.photoUrl)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(MaterialTheme.shapes.small)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = getFirstName(user.displayName),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        } else {
            Log.d("TopTitleBar", "User not logged in or displayName is null.")
        }
    }
}
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
                    if (selectedRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
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


fun getFirstName(fullName: String?): String {
    return fullName?.split(" ")?.getOrNull(0) ?: ""
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

