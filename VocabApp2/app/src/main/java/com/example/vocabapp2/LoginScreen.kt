package com.example.vocabapp2

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vocabapp2.viewModel.MyWordsViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
    var continueAsGuest by remember { mutableStateOf(false) }
    var showAlertDialog by remember { mutableStateOf(false) }
    val launcher = rememberFirebaseAuthLauncher(
        onAuthComplete = { result ->
            user = result.user
        },
        onAuthError = {
            user = null
        }
    )
    val token = stringResource(R.string.default_web_client_id)
    val context = LocalContext.current

    Surface(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .safeDrawingPadding(),
        color = Color(0xFFFFE4C4) // Background color
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Title
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displayMedium,
                color = Color(0xffff6347),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Google Sign-In Button
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xffff6347)),
                elevation = CardDefaults.cardElevation(8.dp),
                onClick = {
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(token)
                        .requestEmail()
                        .build()
                    val googleSignInClient = GoogleSignIn.getClient(context, gso)
                    launcher.launch(googleSignInClient.signInIntent)
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person, // Replace with a Google icon if available
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.google_sign_in),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                }
            }

            // Guest Sign-In Button
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE4C4)),
                elevation = CardDefaults.cardElevation(8.dp),
                onClick = {
                    showAlertDialog = true
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PersonOutline, // Placeholder for guest icon
                        contentDescription = null,
                        tint = Color(0xffff6347),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.guest_sign_in),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xffff6347)
                    )
                }
            }

            if (showAlertDialog) {
                AlertDialog(
                    onDismissRequest = { showAlertDialog = false },
                    title = {
                        Text(
                            text = "Important Notice",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black
                        )
                    },
                    text = {
                        Text(
                            text = stringResource(R.string.progress_warning),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            continueAsGuest = true
                            showAlertDialog = false
                        }) {
                            Text(text = "OK", color = Color(0xffff6347))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showAlertDialog = false
                        }) {
                            Text(text = "Cancel", color = Color.Gray)
                        }
                    }
                )
            }
        }
    }

    // Placeholder for when the user is already signed in or continues as a guest
    if (user != null || continueAsGuest) {
        VocabApp(modifier)
    }
}

@Composable
fun getGoogleSignInIntent(context: Context): GoogleSignInClient {
    val token = stringResource(R.string.default_web_client_id)
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(token)
        .requestEmail()
        .build()
    return  GoogleSignIn.getClient(context, gso)
}

@Composable
fun rememberFirebaseAuthLauncher(
    onAuthComplete: (AuthResult) -> Unit,
    onAuthError: (ApiException) -> Unit,
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val scope = rememberCoroutineScope()
    val myWordsViewModel = viewModel<MyWordsViewModel>()
    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
            scope.launch {
                val authResult = Firebase.auth.signInWithCredential(credential).await()
                onAuthComplete(authResult)
                myWordsViewModel.loadMyWordsList()
            }
        } catch (e: ApiException) {
            onAuthError(e)
        }
    }
}

fun signOut(context: Context) {
    Firebase.auth.signOut()
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
    val googleSignInClient = GoogleSignIn.getClient(context, gso)
    googleSignInClient.signOut()
}