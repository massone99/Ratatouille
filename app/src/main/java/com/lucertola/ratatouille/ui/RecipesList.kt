package com.lucertola.ratatouille.ui

import RatatouilleViewModel
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import com.lucertola.ratatouille.data.Recipe
import com.lucertola.ratatouille.ui.components.RecipeItem


/**
 * Returns a list of recipes.
 * @param recipes The list of recipes to display.
 * @param onViewRecipe The callback to invoke when a recipe is selected.
 */
@Composable
fun RecipesList(
    recipes: List<Recipe>,
    viewModel: RatatouilleViewModel,
    onViewRecipe: (Recipe) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        content = {
            items(recipes.size) { index ->
                RecipeItem(
                    recipe = recipes[index],
                    viewModel = viewModel,
                    onViewRecipe = onViewRecipe
                )
            }
        }
    )
}