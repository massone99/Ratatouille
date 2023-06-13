package com.lucertola.ratatouille.ui.home

import ADD_RECIPE
import RecipeApp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.lucertola.ratatouille.ui.theme.Pink40

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeTopAppBar(navController: NavHostController) {
    TopAppBar(title = {
        Text(
            "Ratatouille", fontWeight = FontWeight.Bold, color = Pink40
        )
    }, actions = {
        val currentRoute = RecipeApp.currentRoute(navController)
        IconButton(onClick = {
            if (currentRoute != ADD_RECIPE) {
                navController.navigate(ADD_RECIPE)
            }
        }) {
            Icon(Icons.Filled.Add, contentDescription = "Add")
        }
    })
}
