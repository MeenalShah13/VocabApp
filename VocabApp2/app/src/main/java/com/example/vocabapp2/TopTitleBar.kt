package com.example.vocabapp2

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.vocabapp2.utils.getFirstName
import com.google.firebase.auth.FirebaseUser

@Composable
fun TopTitleBar(onClickSignedIn: () -> Unit,
                user: FirebaseUser?,
                launcher: ManagedActivityResultLauncher<Intent, ActivityResult>,
                context: Context,
                modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .statusBarsPadding()
            .safeDrawingPadding()
    ) {
        UserMenuButton(onClickSignedIn, launcher, user, context, modifier)

        Spacer(modifier.weight(1f))

        Text(
            text = "VocabApp",
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 30.sp,
            textAlign = TextAlign.Start
        )

        Spacer(modifier.weight(4f))
    }
}

@Composable
fun UserMenuButton(onClickSignedIn: () -> Unit,
                   launcher: ManagedActivityResultLauncher<Intent, ActivityResult>,
                   user: FirebaseUser?,
                   context: Context,
                   modifier: Modifier = Modifier) {
    if (user != null && !user.displayName.isNullOrBlank()) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.clickable { onClickSignedIn() }) {
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
        val googleSignInClient = getGoogleSignInIntent(context)

        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.clickable { launcher.launch(googleSignInClient.signInIntent) }) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
                    .clip(MaterialTheme.shapes.small)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.sign_in),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}