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
import com.example.vocabapp2.model.MyWordsViewModel
import com.example.vocabapp2.ui.theme.VocabApp2Theme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {

    private lateinit var myWordsViewModel: MyWordsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        myWordsViewModel = ViewModelProvider(this).get(MyWordsViewModel::class.java)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            VocabApp2Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LoginScreen(modifier = Modifier)
//                    VocabApp(modifier = Modifier)
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
    VocabApp2Theme {
        Surface(modifier = Modifier.fillMaxSize()) {
            VocabApp(modifier = Modifier)
        }
    }
}