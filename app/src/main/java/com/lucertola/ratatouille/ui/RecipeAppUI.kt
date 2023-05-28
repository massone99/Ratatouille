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
        var showAddDialog by remember { mutableStateOf(false) }
        var showEditDialog by remember { mutableStateOf(false) }
        val initialRecipes = recipesStore.getRecipes()
        val recipes = remember { mutableStateOf(initialRecipes) }
        var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }

        val onAddRecipe: (Recipe) -> Unit = { recipe ->
            recipes.value = recipes.value + recipe
            recipesStore.saveRecipes(recipes.value)
            showAddDialog = false
        }

        val onDeleteRecipe: (Recipe) -> Unit = { recipeToDelete ->
            recipes.value = recipes.value.filter { it != recipeToDelete }
            recipesStore.saveRecipes(recipes.value)
            selectedRecipe = null
        }

        val onEditRecipe: (Recipe) -> Unit = { editedRecipe ->
            selectedRecipe?.let { originalRecipe ->
                val index = recipes.value.indexOf(originalRecipe)
                if (index != -1) {
                    val updatedRecipes = recipes.value.toMutableList()
                    updatedRecipes[index] = editedRecipe
                    recipes.value = updatedRecipes
                    recipesStore.saveRecipes(recipes.value)
                    showEditDialog = false
                    selectedRecipe = null
                }
            }
        }

        Column {
            TopAppBar(title = { Text("Ratatouille") }, actions = {
                IconButton(onClick = { /* Handle refresh action here */ }) {
                    Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
                }
                IconButton(onClick = { showAddDialog = true }) {
                    Icon(Icons.Filled.Add, contentDescription = "Add")
                }
            })
            RecipesList(recipes.value) { recipe ->
                // if i click on a RecupeItem, the selectedRecipe is set to the recipe
                selectedRecipe = recipe
            }
            if (showAddDialog) {
                AddRecipeDialog(onAddRecipe = onAddRecipe,
                    onDismissRequest = { showAddDialog = false })
            }
            if (showEditDialog) {
                selectedRecipe?.let { recipe ->
                    EditRecipeDialog(recipe = recipe,
                        onEditRecipe = onEditRecipe,
                        onDismissRequest = { showEditDialog = false })
                }
            }
            if (!showEditDialog && selectedRecipe != null) {
                ViewRecipeDialog(recipe = selectedRecipe!!,
                    // when exit from the ViewRecipeDialog, the selectedRecipe is set to null so that
                    // no recipe is selected
                    onDismissRequest = { selectedRecipe = null },
                    onDeleteRecipe = onDeleteRecipe,
                    onEditRecipe = { recipe ->
                        showEditDialog = true
                    })
            }
        }
    }
}
