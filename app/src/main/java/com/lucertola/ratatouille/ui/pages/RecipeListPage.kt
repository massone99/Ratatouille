package com.lucertola.ratatouille.ui.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lucertola.ratatouille.data.Recipe
import com.lucertola.ratatouille.ui.components.RecipeItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipesList(recipes: List<Recipe>, onViewRecipe: (Recipe) -> Unit) {
    val gridCells = GridCells.Fixed(2)
    LazyVerticalGrid(
        columns = gridCells,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items(recipes.size) { index ->
            RecipeItem(recipes[index], onViewRecipe)
        }
    }
}
