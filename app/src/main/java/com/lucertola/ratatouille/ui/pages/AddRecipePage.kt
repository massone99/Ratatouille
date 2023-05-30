package com.lucertola.ratatouille.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lucertola.ratatouille.data.Recipe
import com.lucertola.ratatouille.ui.HOME

object AddRecipePage {
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

        Scaffold(content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxWidth(1f)
                    .verticalScroll(
                        enabled = true, state = rememberScrollState()
                    ),
            ) {
                Card(modifier = Modifier.padding(8.dp)) {
                    val padding = 16.dp
                    Column(
                        modifier = Modifier.padding(padding),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Aggiungi una nuova ricetta",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(modifier = Modifier.padding(padding),
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Nome") })
                        OutlinedTextField(modifier = Modifier.padding(padding),
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Descrizione") })
                        OutlinedTextField(modifier = Modifier.padding(padding),
                            value = ingredients,
                            onValueChange = { ingredients = it },
                            label = { Text("Ingredienti") })

                        Row(
                            modifier = Modifier.fillMaxWidth(1f),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(onClick = {
                                onAddRecipe(Recipe(name,
                                    description,
                                    ingredients.split("\n").map { it.trim() }
                                        .filter { it.isNotEmpty() })
                                )
                                navController.navigate(HOME)
                            }) {
                                Text("Add")
                            }
                            Button(onClick = { navController.navigateUp() }) {
                                Text("Cancel")
                            }
                        }
                        Spacer(
                            Modifier.height(16.dp)
                        )
                    }
                }
            }
        })
    }
}