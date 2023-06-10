package com.lucertola.ratatouille.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lucertola.ratatouille.data.Recipe
import com.lucertola.ratatouille.data.RecipesStore
import com.lucertola.ratatouille.ui.RecipesList.RecipesList
import com.lucertola.ratatouille.ui.ShoppingPage.ShoppingPage
import com.lucertola.ratatouille.ui.pages.AddRecipePage.AddRecipePage
import com.lucertola.ratatouille.ui.pages.EditRecipePage.EditRecipePage
import com.lucertola.ratatouille.ui.pages.ViewRecipePage.ViewRecipePage
import com.lucertola.ratatouille.ui.theme.PastelYellowBackground
import com.lucertola.ratatouille.ui.theme.PastelYellowOnSurface

const val HOME = "RecipesListPage"
const val VIEW_RECIPE = "ViewRecipePage"
const val ADD_RECIPE = "AddRecipePage"
const val EDIT_RECIPE = "EditRecipePage"
const val SHOPPING_PAGE = "ShoppingPage"

object RecipeApp {
    @SuppressLint("MutableCollectionMutableState")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun RecipeApp(context: Context) {
        val navController = rememberNavController()
        val recipesStore = RecipesStore(context)
        val initialRecipes = recipesStore.getRecipes()
        val recipes = remember { mutableStateOf(initialRecipes) }
        var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }
        val shoppingRecipes by remember { mutableStateOf(mutableStateListOf<Recipe>()) }


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


        Scaffold(

            topBar = {
                TopAppBar(title = { Text("Ratatouille") }, actions = {
                    val currentRoute = currentRoute(navController)
                    IconButton(onClick = {
                        if (currentRoute != ADD_RECIPE) {
                            navController.navigate(ADD_RECIPE)
                        }
                    }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add")
                    }
                })
            },
            bottomBar = {
                val backgroundColor = if (isSystemInDarkTheme()) {
                    Color.Black // Set dark theme color
                } else {
                    Color.White // Set light theme color
                }

                val contentColor = if (isSystemInDarkTheme()) {
                    Color.White // Set dark theme color
                } else {
                    Color.Black // Set light theme color
                }

                BottomNavigation(
                    backgroundColor = backgroundColor,
                    contentColor = contentColor
                ) {
                    // by default the current route is HOME
                    val currentRoute = currentRoute(navController)
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                        selected = currentRoute == HOME,
                        onClick = { navController.navigate(HOME) }
                    )
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Shopping") },
                        selected = currentRoute == SHOPPING_PAGE,
                        onClick = { navController.navigate(SHOPPING_PAGE) }
                    )
                }
            }
        ) {
            Column(modifier = Modifier.padding(it)) {
                NavHost(navController = navController, startDestination = HOME) {
                    composable(HOME) {
                        RecipesList(recipes.value) { recipe ->
                            selectedRecipe = recipe
                            navController.navigate(VIEW_RECIPE)
                        }
                    }
                    composable(VIEW_RECIPE) {
                        selectedRecipe?.let { recipe ->
                            ViewRecipePage(recipe, onDeleteRecipe) {
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
                    composable(SHOPPING_PAGE) {
                        ShoppingPage(recipes = shoppingRecipes)
                    }
                }
            }
        }
    }

    @Composable
    fun currentRoute(navController: NavHostController): String? {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        return navBackStackEntry?.destination?.route
    }
}
