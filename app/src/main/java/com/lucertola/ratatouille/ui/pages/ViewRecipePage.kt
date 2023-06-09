package com.lucertola.ratatouille.ui.pages

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lucertola.ratatouille.data.Recipe
import com.lucertola.ratatouille.ui.theme.ButtonBackgroundDark
import com.lucertola.ratatouille.ui.theme.ButtonBackgroundLight
import com.lucertola.ratatouille.ui.theme.CardBackgroundDark
import com.lucertola.ratatouille.ui.theme.CardBackgroundLight

object ViewRecipePage {
    /**
     * Return a RecipeDialog ( a view on a recipe ).
     * @param recipe The recipe to display.
     * @param onDeleteRecipe The callback to invoke when the recipe is deleted.
     * @param onEditRecipe The callback to invoke when the recipe is edited.
     */
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ViewRecipePage(
        recipe: Recipe, onDeleteRecipe: (Recipe) -> Unit, onEditRecipe: (Recipe) -> Unit
    ) {
        Scaffold(content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxWidth(1f)
            ) {
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(1f),
                    colors = CardDefaults.cardColors(
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        containerColor = if (isSystemInDarkTheme()) CardBackgroundDark else CardBackgroundLight,
                    ),
                    elevation = CardDefaults.elevatedCardElevation(4.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(1f)
                    ) {
                        // add a text with name and h1
                        Column(
                            modifier = Modifier.fillMaxWidth(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(recipe.name, style = MaterialTheme.typography.headlineMedium)
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Descrizione:", style = MaterialTheme.typography.labelLarge)
                        Text(recipe.description, style = MaterialTheme.typography.bodySmall)
                        Text("\n")
                        Text("Ingredienti:", style = MaterialTheme.typography.labelLarge)
                        Text(
                            if (recipe.ingredients.isEmpty()) "Nessun ingrediente specificato"
                            else recipe.ingredients.joinToString(separator = "\n") { "${it.name}  ${if (it.grams.isBlank()) "" else "- " + it.grams + "gr"}" },
                            style = MaterialTheme.typography.bodySmall
                        )


                        // add some space between the text and the buttons
                        Spacer(
                            modifier = Modifier
                                .padding(8.dp)
                                .height(8.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(1f),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            Button(
                                onClick = { onEditRecipe(recipe) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSystemInDarkTheme()) ButtonBackgroundDark else ButtonBackgroundLight,
                                    contentColor = Color.Black,
                                )
                            ) {
                                Text("Modifica")
                            }
                            Button(
                                onClick = { onDeleteRecipe(recipe) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSystemInDarkTheme()) ButtonBackgroundDark else ButtonBackgroundLight,
                                    contentColor = Color.Black,
                                )
                            ) {
                                Text("Cancella")
                            }
                        }
                    }
                }
            }
        })
    }
}