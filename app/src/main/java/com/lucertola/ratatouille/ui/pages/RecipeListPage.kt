package com.lucertola.ratatouille.ui.pages

import RatatouilleViewModel
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction.Companion
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lucertola.ratatouille.R
import com.lucertola.ratatouille.data.Recipe
import com.lucertola.ratatouille.ui.components.RecipeItem.RecipeItem

@Composable
fun RecipesListPage(
    recipes: List<Recipe>, viewModel: RatatouilleViewModel, onViewRecipe: (Recipe) -> Unit
) {
    val gridCells = GridCells.Fixed(2)
    Column(modifier = Modifier) {
        if (viewModel.isSearchBarVisible.value) {
            SearchBar(viewModel)
        }
        if (recipes.isNotEmpty()) {
            RecipeList(recipes, gridCells, viewModel, onViewRecipe)
        } else {
            EmptyState()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(viewModel: RatatouilleViewModel) {
    var searchQuery by remember { mutableStateOf("") }
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
        label = { Text(stringResource(R.string.find)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = Companion.Search,
            capitalization = KeyboardCapitalization.Words
        ),
        keyboardActions = KeyboardActions(onSearch = {
            viewModel.search(searchQuery)
        }),
        singleLine = true
    )
}

@Composable
fun RecipeList(
    recipes: List<Recipe>,
    gridCells: GridCells,
    viewModel: RatatouilleViewModel,
    onViewRecipe: (Recipe) -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(30.dp)) {
        Spacer(modifier = Modifier.weight(1f)) // This will push the button to the end
        IconButton(onClick = { viewModel.toggleSortRecipes() }) {
            Icon(
                painter = painterResource(id = R.drawable.sort_by_alpha),
                tint = if (viewModel.isSorted.value) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
                contentDescription = "Sort Alphabetically"
            )
        }
    }

    LazyVerticalGrid(
        columns = gridCells, modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items(recipes.size) { index ->
            RecipeItem(recipes[index], viewModel, onViewRecipe)
        }
    }
}

@Composable
fun EmptyState() {
    Text(
        textAlign = TextAlign.Center,
        text = stringResource(R.string.no_recipes_found),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
}
