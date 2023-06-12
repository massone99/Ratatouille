package com.lucertola.ratatouille.ui.pages

import RecipeViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.lucertola.ratatouille.data.Recipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRecipePage(
    recipe: Recipe,
    viewModel: RecipeViewModel,
    navController: NavController,
    onEditRecipe: (Recipe) -> Unit
) {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
        ) {
            RecipeForm("Modifica la ricetta", viewModel, recipe, onEditRecipe, navController)
        }
    }
}
