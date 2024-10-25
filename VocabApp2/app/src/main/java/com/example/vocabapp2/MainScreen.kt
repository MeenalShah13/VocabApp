package com.example.vocabapp2

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen() {
    Scaffold {innerPadding ->
        Text(text = "Home Screen", modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun CategoriesScreen() {
    Scaffold {innerPadding ->
        Text(text = "Categories Screen", modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun FavoritesScreen() {
    Scaffold {innerPadding ->
        Text(text = "Favorites Screen", modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun ProfileScreen() {
    Scaffold {innerPadding ->
        Text(text = "Profile Screen", modifier = Modifier.padding(innerPadding))
    }
}
