package com.lucertola.ratatouille.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.lucertola.ratatouille.data.Recipe

object ViewRecipePage {
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

}