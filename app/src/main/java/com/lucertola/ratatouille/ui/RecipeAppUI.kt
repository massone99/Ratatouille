package com.lucertola.ratatouille.ui

import android.content.Context
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
import com.lucertola.ratatouille.data.RecipesStore

object RecipeAppUI {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun RecipeApp(context: Context) {
        val recipesStore = RecipesStore(context)
        var showDialog by remember { mutableStateOf(false) }
        val initialRecipes = recipesStore.getRecipes()
        val recipes = remember { mutableStateOf(initialRecipes) }
        var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }

        val onAddRecipe: (Recipe) -> Unit = { recipe ->
            recipes.value = recipes.value + recipe
            recipesStore.saveRecipes(recipes.value)
            showDialog = false
        }

        // When a recipe is deleted
        val onDeleteRecipe: (Recipe) -> Unit = { recipeToDelete ->
            recipes.value = recipes.value.filter { it != recipeToDelete }
            recipesStore.saveRecipes(recipes.value)
            selectedRecipe = null
        }


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
                AddRecipeDialog(onAddRecipe = onAddRecipe, onDismissRequest = { showDialog = false })
            }
            selectedRecipe?.let {
                RecipeDialog(recipe = it,
                    onDismissRequest = { selectedRecipe = null },
                    onDeleteRecipe = onDeleteRecipe
                )
            }
        }
    }
}
