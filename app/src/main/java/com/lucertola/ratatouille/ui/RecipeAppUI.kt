package com.lucertola.ratatouille.ui

import AddRecipePage
import EditRecipePage
import ViewRecipePage
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lucertola.ratatouille.data.RecipesStore

object RecipeAppUI {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun RecipeApp(context: Context) {
        val recipesStore = RecipesStore(context)
        val initialRecipes = recipesStore.getRecipes()
        val navController = rememberNavController()

        Column {
            TopAppBar(title = { Text("Ratatouille") }, actions = {
                IconButton(onClick = { /* Handle refresh action here */ }) {
                    Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
                }
                IconButton(onClick = { navController.navigate("add") }) {
                    Icon(Icons.Filled.Add, contentDescription = "Add")
                }
            })

            NavHost(navController, startDestination = "recipes") {
                composable("recipes") {
                    RecipesList(initialRecipes) { recipe ->
                        navController.navigate("view/${recipe.name}")
                    }
                }
                composable("add") {
                    AddRecipePage(onAddRecipe = {
                        // Handle adding recipe and go back to list.
                        navController.navigate("recipes")
                    }, onDismissRequest = {
                        navController.navigate("recipes")
                    })
                }
                composable(
                    "view/{recipeName}",
                    arguments = listOf(navArgument("recipeName") { type = NavType.StringType })
                ) { backStackEntry ->
                    val recipeName = backStackEntry.arguments?.getString("recipeName")
                    val recipe = initialRecipes.find { it.name == recipeName }
                    recipe?.let {
                        ViewRecipePage(recipe = it, onDeleteRecipe = {
                            // Handle deletion and go back to list.
                            navController.navigate("recipes")
                        }, onEditRecipe = { recipe ->
                            navController.navigate("edit/${recipe.name}")
                        }, onDismissRequest = {
                            navController.navigate("recipes")
                        })
                    }
                }
                composable(
                    "edit/{recipeName}",
                    arguments = listOf(navArgument("recipeName") { type = NavType.StringType })
                ) { backStackEntry ->
                    val recipeName = backStackEntry.arguments?.getString("recipeName")
                    val recipe = initialRecipes.find { it.name == recipeName }
                    recipe?.let {
                        EditRecipePage(recipe = it, onEditRecipe = {
                            // Handle editing and go back to list.
                            navController.navigate("recipes")
                        }, onDismissRequest = {
                            navController.navigate("recipes")
                        })
                    }
                }
            }
        }
    }
}
