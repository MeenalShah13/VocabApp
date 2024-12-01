package com.example.vocabapp2

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.vocabapp2.model.BottomNavItem
import com.example.vocabapp2.utils.getCurrentUser
import com.example.vocabapp2.viewModel.CourseViewModel
import com.example.vocabapp2.viewModel.MyWordsViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@Composable
fun VocabApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val context: Context = LocalContext.current
    val firestoreDatabase: FirebaseFirestore = FirebaseFirestore.getInstance()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val courseViewModel: CourseViewModel = viewModel()
    val myWordsViewModel: MyWordsViewModel = viewModel()
    var user by remember { mutableStateOf(getCurrentUser()) }
    val scope = rememberCoroutineScope()
    val launcher = rememberFirebaseAuthLauncher(onAuthComplete = {result ->
        user = result.user
    }, onAuthError = {
        user = null
    })

    // Track back press
    var backPressedTime by remember { mutableLongStateOf(0L) }
    // Handle back button press
    BackHandler {
        val currentTime = System.currentTimeMillis()
        if (currentTime - backPressedTime < 2000) {
            // Exit the app if pressed twice within 2 seconds
            exitProcess(status = 0)
        } else {
            backPressedTime = currentTime
            Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                UserProfileMenu(user, {
                    user = null
                    navController.popBackStack(BottomNavItem.Courses.route, true)
                }, context, modifier)
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
                }, user, launcher, context, modifier)
            },
            bottomBar = {
                BottomNavigationBar(
                    navController = navController
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
}