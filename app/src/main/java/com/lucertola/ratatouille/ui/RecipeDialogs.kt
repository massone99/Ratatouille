package com.lucertola.ratatouille.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.lucertola.ratatouille.data.Recipe

/**
 * Return a RecipeDialog ( a view on a recipe ).
 * @param recipe The recipe to display.
 * @param onDismissRequest The callback to invoke when the dialog is dismissed.
 * @param onDeleteRecipe The callback to invoke when the recipe is deleted.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewRecipePage(
    recipe: Recipe,
    navController: NavController,
    onDeleteRecipe: (Recipe) -> Unit,
    onEditRecipe: (Recipe) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(recipe.name) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        content = {
            Column(modifier = Modifier.padding(it)) {
                Column {
                    Text("Descrizione:")
                    Text(recipe.description)
                    Text("\n")
                    Text("Ingredienti:")
                    Text(recipe.ingredients.joinToString(", "))

                    Row {
                        Button(onClick = { onEditRecipe(recipe) }) {
                            Text("Edit")
                        }
                        Button(onClick = { onDeleteRecipe(recipe) }) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    )
}

/**
 * Return a AddRecipeDialog ( a view to add a recipe ).
 * @param onAddRecipe The callback to invoke when a recipe is added.
 * @param onDismissRequest The callback to invoke when the dialog is dismissed.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipePage(navController: NavController, onAddRecipe: (Recipe) -> Unit) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add a new recipe") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        content = {
            Column(modifier = Modifier.padding(it)) {
                Column {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") })
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") })
                    OutlinedTextField(
                        value = ingredients,
                        onValueChange = { ingredients = it },
                        label = { Text("Ingredients") })

                    Button(onClick = {
                        onAddRecipe(Recipe(name, description, ingredients.split(",")))
                        navController.navigate(HOME)
                    }) {
                        Text("Add")
                    }

                    Button(onClick = { navController.navigateUp() }) {
                        Text("Cancel")
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRecipePage(recipe: Recipe, navController: NavController, onEditRecipe: (Recipe) -> Unit) {
    var name by remember { mutableStateOf(recipe.name) }
    var description by remember { mutableStateOf(recipe.description) }
    var ingredients by remember { mutableStateOf(recipe.ingredients.joinToString(",")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit recipe") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        content = {
            Column(modifier = Modifier.padding(it)) {
                Column {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") })
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") })
                    OutlinedTextField(
                        value = ingredients,
                        onValueChange = { ingredients = it },
                        label = { Text("Ingredients") })

                    Button(onClick = {
                        onEditRecipe(Recipe(name, description, ingredients.split(",")))
                        navController.navigateUp()
                    }) {
                        Text("Edit")
                    }

                    Button(onClick = { navController.navigateUp() }) {
                        Text("Cancel")
                    }
                }
            }
        }
    )
}


