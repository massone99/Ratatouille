package com.lucertola.ratatouille.ui.home

import ADD_RECIPE
import RatatouilleViewModel
import RecipeApp
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.lucertola.ratatouille.ui.theme.ButtonBackgroundDark
import com.lucertola.ratatouille.ui.theme.ButtonBackgroundLight

object HomeTopAppBar {
    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    fun HomeTopAppBar(navController: NavHostController, viewModel: RatatouilleViewModel) {
        TopAppBar(title = {
            Text(
                "Ratatouille", fontWeight = FontWeight.Bold
            )
        }, actions = {
            val currentRoute = RecipeApp.currentRoute(navController)
            IconButton(onClick = {
                viewModel.setSearchBarVisibility(viewModel.isSearchBarVisible.value.not())
            }) {
                // if viewModel.isSearchBarVisible.value is true color is Pink80 else White
                if (viewModel.isSearchBarVisible.value) {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = if (isSystemInDarkTheme()) ButtonBackgroundDark else ButtonBackgroundLight
                    )
                } else {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
            }
            IconButton(onClick = {
                if (currentRoute != ADD_RECIPE) {
                    navController.navigate(ADD_RECIPE)
                }
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        })
    }

}