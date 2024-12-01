package com.example.vocabapp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.example.compose.AppTheme
import com.example.vocabapp2.viewModel.MyWordsViewModel
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {

    private lateinit var myWordsViewModel: MyWordsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        myWordsViewModel = ViewModelProvider(this)[MyWordsViewModel::class.java]
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            AppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LoginScreen(modifier = Modifier)
                }
            }
        }
    }

    override fun onPause() {
        myWordsViewModel.saveWordsToCloud()
        super.onPause()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            VocabApp(modifier = Modifier)
        }
    }
}

