package com.lucertola.ratatouille.ui.pages

import RatatouilleViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.lucertola.ratatouille.data.Ingredient

object ShoppingPage {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ShoppingPage(ingredients: List<Ingredient>, viewModel: RatatouilleViewModel) {
        val checkedIngredients = remember { mutableStateMapOf<Ingredient, Boolean>() }

        ingredients.distinctBy { it.id }.forEach { ingredient ->
            // if no ingredient with it.id is present in the map, add it
            if (!checkedIngredients.containsKey(ingredient)) {
                checkedIngredients[ingredient] = false
            }
        }

        val anyChecked = checkedIngredients.values.any { it }

        Scaffold(
            content = {
                Column(modifier = Modifier.padding(it)) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                    ) {
                        if (ingredients.isNotEmpty()) {
                            LazyColumn(modifier = Modifier.weight(1f)) {
                                items(ingredients.size) { index ->
                                    ShoppingItem(ingredients[index], checkedIngredients)
                                    Divider()
                                }
                            }
                        } else {
                            Text(
                                textAlign = TextAlign.Center,
                                text = "Nessun ingrediente da comprare!",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier
                                    .padding(vertical = 16.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            },
            // Only show the button if at least one ingredient is checked
            floatingActionButton = {
                if (anyChecked) {
                    FloatingActionButton(onClick = {
                        // remove from the shopping list all the ingredients that are checked using the view model
                        checkedIngredients.filter { it.value }.forEach { (ingredient, _) ->
                            viewModel.removeIngredientFromShoppingList(ingredient)
                        }
                    }) {
                        Icon(Icons.Default.Done, contentDescription = "Complete Shopping")
                    }
                }
            }
        )
    }

    @Composable
    fun ShoppingItem(ingredient: Ingredient, checkedIngredients: MutableMap<Ingredient, Boolean>) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            val isIngredientChecked = checkedIngredients[ingredient] ?: false
            Checkbox(checked = isIngredientChecked, onCheckedChange = { isChecked ->
                checkedIngredients[ingredient] = isChecked
            })
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                // add the id of the ingredient to the text to make it unique
                text = "${ingredient.name} ${if (ingredient.grams != "") "(${ingredient.grams} gr)" else ""}",
                style = if (isIngredientChecked) MaterialTheme.typography.bodyLarge.copy(
                    textDecoration = TextDecoration.LineThrough
                ) else MaterialTheme.typography.bodyLarge
            )
        }
    }
}
