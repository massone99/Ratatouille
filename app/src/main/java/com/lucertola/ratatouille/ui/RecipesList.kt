package com.lucertola.ratatouille.ui

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.lucertola.ratatouille.data.Recipe


/**
 * Returns a list of recipes.
 * @param recipes The list of recipes to display.
 * @param onViewRecipe The callback to invoke when a recipe is selected.
 */
@Composable
fun RecipesList(recipes: List<Recipe>, onViewRecipe: (Recipe) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        content = {
            items(recipes.size) { index ->
                RecipeItem(
                    recipe = recipes[index],
                    onViewRecipe = onViewRecipe
                )
            }
        }
    )
}


@Composable
@Preview
fun PreviewRecipeItem() {
    RecipeItem(
        recipe = Recipe(
            name = "Pasta al pomodoro",
            description = "Pasta al pomodoro",
            ingredientsToGrams = listOf(
                Pair("Pasta", "100g"),
                Pair("Pomodoro", "100g"),
                Pair("Olio", "10g"),
                Pair("Sale", "1g"),
            )
        ),
        onViewRecipe = {}
    )
}
