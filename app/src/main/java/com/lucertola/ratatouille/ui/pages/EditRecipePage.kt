package com.lucertola.ratatouille.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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

object EditRecipePage {
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
                        }) {
                            Text("Confirm")
                        }

                        Button(onClick = { navController.navigateUp() }) {
                            Text("Cancel")
                        }
                    }
                }
            }
        )
    }

}