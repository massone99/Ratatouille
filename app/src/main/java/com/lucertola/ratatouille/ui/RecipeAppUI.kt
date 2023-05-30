package com.lucertola.ratatouille.ui

import android.content.Context
import android.util.Log
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lucertola.ratatouille.data.Recipe
import com.lucertola.ratatouille.data.RecipesStore
import com.lucertola.ratatouille.ui.pages.AddRecipePage.AddRecipePage
import com.lucertola.ratatouille.ui.pages.EditRecipePage.EditRecipePage
import com.lucertola.ratatouille.ui.pages.ViewRecipePage.ViewRecipePage

const val HOME = "RecipesListPage"
const val VIEW_RECIPE = "ViewRecipePage"
const val ADD_RECIPE = "AddRecipePage"
const val EDIT_RECIPE = "EditRecipePage"

object RecipeAppUI {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun RecipeApp(context: Context) {
        val navController = rememberNavController()
        val recipesStore = RecipesStore(context)
        val initialRecipes = recipesStore.getRecipes()
        val recipes = remember { mutableStateOf(initialRecipes) }
        var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }

        val onAddRecipe: (Recipe) -> Unit = { recipe ->
            recipes.value = recipes.value + recipe
            recipesStore.saveRecipes(recipes.value)
            navController.navigate(HOME)
        }

        val onDeleteRecipe: (Recipe) -> Unit = { recipeToDelete ->
            recipes.value = recipes.value.filter { it != recipeToDelete }
            recipesStore.saveRecipes(recipes.value)
            selectedRecipe = null
            navController.navigate(HOME)
        }

        val onEditRecipe: (Recipe) -> Unit = { editedRecipe ->
            selectedRecipe?.let { originalRecipe ->
                val index = recipes.value.indexOf(originalRecipe)
                if (index != -1) {
                    val updatedRecipes = recipes.value.toMutableList()
                    updatedRecipes[index] = editedRecipe
                    recipes.value = updatedRecipes
                    recipesStore.saveRecipes(recipes.value)
                    selectedRecipe = null
                }
            }
            selectedRecipe = editedRecipe
            navController.popBackStack()
        }
        Column {
            TopAppBar(title = { Text("Ratatouille") }, actions = {
                IconButton(onClick = { /* Handle refresh action here */ }) {
                    Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
                }
                IconButton(onClick = { navController.navigate(ADD_RECIPE) }) {
                    Icon(Icons.Filled.Add, contentDescription = "Add")
                }
            })
            NavHost(navController = navController, startDestination = HOME) {
                composable(HOME) {
                    RecipesList(recipes.value) { recipe ->
                        selectedRecipe = recipe
                        navController.navigate(VIEW_RECIPE)
                    }
                }
                composable(VIEW_RECIPE) {
                    selectedRecipe?.let { recipe ->
                        ViewRecipePage(recipe, navController, onDeleteRecipe) {
                            navController.navigate(EDIT_RECIPE)
                        }
                    }
                }
                composable(ADD_RECIPE) {
                    AddRecipePage(navController, onAddRecipe)
                }
                composable(EDIT_RECIPE) {
                    selectedRecipe?.let { recipe ->
                        EditRecipePage(recipe, navController, onEditRecipe)
                    }
                }
            }

        }
    }
}
