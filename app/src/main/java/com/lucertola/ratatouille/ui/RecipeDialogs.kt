package com.lucertola.ratatouille.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.lucertola.ratatouille.data.Recipe

@Composable
fun RecipeDialog(recipe: Recipe, onDismissRequest: () -> Unit) {
    AlertDialog(onDismissRequest = onDismissRequest,
        title = { Text(recipe.name) },
        text = { Text(recipe.description) },
        confirmButton = {
            Button(onClick = onDismissRequest) {
                Text("OK")
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeDialog(onAddRecipe: (Recipe) -> Unit, onDismissRequest: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }

    AlertDialog(onDismissRequest = onDismissRequest, title = { Text("Add a new recipe") }, text = {
        Column {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
            OutlinedTextField(value = description,
                onValueChange = { description = it },
                label = { Text("Description") })
            OutlinedTextField(value = ingredients,
                onValueChange = { ingredients = it },
                label = { Text("Ingredients") })
        }
    }, confirmButton = {
        Button(onClick = {
            onAddRecipe(Recipe(name, description))
        }) {
            Text("Add")
        }
    }, dismissButton = {
        Button(onClick = onDismissRequest) {
            Text("Cancel")
        }
    })
}
