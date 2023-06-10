package com.lucertola.ratatouille.ui.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lucertola.ratatouille.data.Recipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeForm(
    title: String,
    recipe: Recipe,
    onFormResult: (Recipe) -> Unit,
    navController: NavController
) {
    var name by remember { mutableStateOf(recipe.name) }
    var description by remember { mutableStateOf(recipe.description) }
    var ingredientsFields by remember { mutableStateOf(recipe.ingredientsToGrams.map { mutableStateOf(it) }) }
    var ingredientName by remember { mutableStateOf("") }
    var ingredientGrams by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(
                enabled = true, state = rememberScrollState()
            )
    ) {
        Card(modifier = Modifier.padding(8.dp)) {
            val padding = 16.dp
            Column(
                modifier = Modifier.padding(padding).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(padding))
                OutlinedTextField(modifier = Modifier.padding(padding),
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome") })
                OutlinedTextField(modifier = Modifier.padding(padding),
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descrizione") })

                for (ingredientField in ingredientsFields) {
                    var (ingredientName, ingredientGrams) = ingredientField.value
                    Row(
                        modifier = Modifier.width(310.dp),
                    ) {
                        OutlinedTextField(modifier = Modifier
                            .padding(padding)
                            .weight(1f)
                            .fillMaxHeight(),
                            value = ingredientName,
                            onValueChange = { newValue ->
                                ingredientName = newValue
                                ingredientField.value = ingredientName to ingredientGrams
                            },
                            label = {
                                Text(
                                    "Ingrediente", style =
                                    MaterialTheme.typography.bodySmall
                                )
                            })
                        OutlinedTextField(
                            modifier = Modifier
                                .padding(padding)
                                .weight(1f)
                                .fillMaxHeight(),
                            value = ingredientGrams,
                            onValueChange = { newGrams ->
                                ingredientGrams = newGrams
                                ingredientField.value = ingredientName to ingredientGrams
                            },
                            label = {
                                Text(
                                    "Grammi", style =
                                    MaterialTheme.typography.bodySmall
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                }

                Row(
                    modifier = Modifier.width(310.dp),
                ) {
                    Button(onClick = {
                        ingredientsFields = ingredientsFields + mutableStateOf(ingredientName to ingredientGrams)
                        ingredientName = ""
                        ingredientGrams = ""
                    }) {
                        Text("+")
                    }
                    Button(onClick = {
                        val ingredientsToGrams = ingredientsFields.map { it.value }
                        onFormResult(
                            Recipe(
                                name,
                                description,
                                ingredientsToGrams
                            )
                        )
                    }) {
                        Text("Confirm")
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
}

object EditRecipePage {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun EditRecipePage(
        recipe: Recipe, navController: NavController, onEditRecipe: (Recipe) -> Unit
    ) {
        Scaffold {
            Column(modifier = Modifier.padding(it)) {
                RecipeForm("Modifica la ricetta", recipe, onEditRecipe, navController)
            }
        }
    }
}

object AddRecipePage {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AddRecipePage(navController: NavController, onAddRecipe: (Recipe) -> Unit) {
        val recipe = Recipe("", "", listOf())
        Scaffold {
            Column(modifier = Modifier.padding(it)) {
                RecipeForm("Aggiungi una ricetta", recipe, onAddRecipe, navController)
            }
        }
    }
}
