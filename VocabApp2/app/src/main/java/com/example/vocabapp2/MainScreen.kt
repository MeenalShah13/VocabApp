package com.example.vocabapp2

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
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
import com.example.vocabapp2.utils.getCurrentUser
import com.example.vocabapp2.viewModel.CourseViewModel
import com.example.vocabapp2.viewModel.MyWordsViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch


@SuppressLint("RememberReturnType")
@Composable
fun MainScreen(navController: NavHostController, context: Context, modifier: Modifier = Modifier) {
    val mainScreenNavController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val firestoreDatabase: FirebaseFirestore = FirebaseFirestore.getInstance()
    var user: FirebaseUser? = getCurrentUser()
    val courseViewModel: CourseViewModel = viewModel()
    val myWordsViewModel: MyWordsViewModel = viewModel()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                UserProfileMenu(user, {
                    user = null
                    mainScreenNavController.popBackStack(BottomNavItem.Courses.route, true)
                }, navController, context, modifier)
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopTitleBar({
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }, user, context, modifier)
            },
            bottomBar = {
                BottomNavigationBar(
                    mainScreenNavController = mainScreenNavController,
                    modifier = modifier
                )
            },
            modifier = modifier.fillMaxSize()
        ) { innerPadding ->
            Surface(
                modifier = modifier.fillMaxSize()
                    .padding(innerPadding)
                    .statusBarsPadding()
                    .safeDrawingPadding()
            ) {
                MainScreenNavHostContainer(
                    courseViewModel,
                    myWordsViewModel,
                    mainScreenNavController,
                    firestoreDatabase,
                    context,
                    modifier
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopTitleBar(onClick: () -> Unit, user: FirebaseUser?, context: Context, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (user != null) {
                    UserMenuButton(onClick, user, context, modifier)
                }
                Spacer(modifier.weight(1f))
                Text(
                    text = "VocabApp",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier.weight(4f))
            }
        },
        modifier = modifier
    )
}

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
fun UserProfileMenu(user: FirebaseUser?, onSignOut:() -> Unit, navController: NavHostController, context: Context, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        if (user != null) {
            AsyncImage(model = ImageRequest.Builder(context)
                .data(user.photoUrl)
                .crossfade(true)
                .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .size(75.dp)
                    .clip(CircleShape))
            Spacer(modifier.height(16.dp))
            Text(text = user.displayName.toString(),
                style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier.height(16.dp))
            HorizontalDivider(color = Color.Gray, thickness = 1.dp)
            Spacer(modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Email:", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier.width(3.dp))
                Text(text = user.email.toString(), style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier.weight(9f))
            Button(onClick = {
                signOut()
                onSignOut()
                navController.navigate("LoginScreen") {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }) {
                Text(text = "Sign Out")
            }
        } else {
            Text(stringResource(R.string.user_not_logged_in))
        }
    }
}

@Composable
fun UserMenuButton(onClick: () -> Unit, user: FirebaseUser, context: Context, modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(16.dp).clickable(onClick = onClick)) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(user.photoUrl)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .size(25.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier.width(5.dp))
            Text(
                text = getFirstName(user.displayName),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
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