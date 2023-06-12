package com.lucertola.ratatouille.ui

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.lucertola.ratatouille.data.Ingredient
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
            ingredients = listOf(
                Ingredient(name = "Pasta", grams = "100"),
                Ingredient(name = "Pomodoro", grams = "100")
            )
        ),
        onViewRecipe = {}
    )
}
