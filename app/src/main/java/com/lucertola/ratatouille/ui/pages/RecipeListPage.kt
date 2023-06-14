package com.lucertola.ratatouille.ui.pages

import RatatouilleViewModel
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction.Companion
import androidx.compose.ui.unit.dp
import com.lucertola.ratatouille.data.Recipe
import com.lucertola.ratatouille.ui.components.RecipeItem.RecipeItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesList(
    recipes: List<Recipe>,
    viewModel: RatatouilleViewModel,
    onViewRecipe: (Recipe) -> Unit
) {
    val gridCells = GridCells.Fixed(2)
    var searchQuery by remember { mutableStateOf("") }
    Column(modifier = Modifier) {
        if (viewModel.isSearchBarVisible.value) {
            BackHandler(onBack = {
                searchQuery = ""
                viewModel.search(searchQuery)
                viewModel.isSearchBarVisible.value = false
            })
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { query ->
                    searchQuery = query
                    viewModel.search(query)
                },
                label = { Text("Cerca") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                keyboardOptions = KeyboardOptions(imeAction = Companion.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        viewModel.search(searchQuery)
                    }
                ),
                singleLine = true
            )
        }
        LazyVerticalGrid(
            columns = gridCells,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(recipes.size) { index ->
                RecipeItem(recipes[index], viewModel, onViewRecipe)
            }
        }
    }
}
