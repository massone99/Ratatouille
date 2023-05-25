package com.lucertola.ratatouille.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.lucertola.ratatouille.data.Recipe

object RecipeAppUI {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun RecipeApp() {
        val recipes = remember { mutableStateOf(listOf<Recipe>()) }
        var showDialog by remember { mutableStateOf(false) }
        var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }

        Column {
            TopAppBar(title = { Text("Recipes App") }, actions = {
                IconButton(onClick = { /* Handle refresh action here */ }) {
                    Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
                }
                IconButton(onClick = { showDialog = true }) {
                    Icon(Icons.Filled.Add, contentDescription = "Add")
                }
            })
            RecipesList(recipes.value) { recipe -> selectedRecipe = recipe }
            if (showDialog) {
                AddRecipeDialog(onAddRecipe = { recipe ->
                    // add recipe to recipes list
                    recipes.value = recipes.value + recipe
                    showDialog = false
                }, onDismissRequest = { showDialog = false })
            }
            selectedRecipe?.let {
                RecipeDialog(
                    recipe = it,
                    onDismissRequest = { selectedRecipe = null },
                    onDeleteRecipe = { recipeToDelete ->
                        recipes.value = recipes.value.filter { it != recipeToDelete }
                        selectedRecipe = null
                    })
            }
        }
    }
}
