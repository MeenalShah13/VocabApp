package com.example.vocabapp2

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.vocabapp2.utils.getFirstName
import com.google.firebase.auth.FirebaseUser

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