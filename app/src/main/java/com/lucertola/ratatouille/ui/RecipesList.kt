package com.lucertola.ratatouille.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lucertola.ratatouille.data.Recipe


/**
 * Returns a list of recipes.
 * @param recipes The list of recipes to display.
 * @param onViewRecipe The callback to invoke when a recipe is selected.
 */
@Composable
fun RecipesList(recipes: List<Recipe>, onViewRecipe: (Recipe) -> Unit) {
    LazyColumn {
        items(recipes.size) { index ->
            RecipeItem(recipes[index], onViewRecipe)
        }
    }
}

/**
 * Returns a single recipe.
 * @param recipe The recipe to display.
 * @param onViewRecipe The callback to invoke when a recipe is selected.
 */
@Composable
fun RecipeItem(recipe: Recipe, onViewRecipe: (Recipe) -> Unit) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column {
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = recipe.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = recipe.ingredients.joinToString(", "),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp)
            )


            Button(onClick = { onViewRecipe(recipe) }, modifier = Modifier.padding(8.dp)) {
                Text("View Recipe")
            }
        }
    }
}
