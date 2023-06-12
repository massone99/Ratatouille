package com.lucertola.ratatouille.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.lucertola.ratatouille.data.Recipe
import com.lucertola.ratatouille.ui.components.RecipeForm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipePage(
    onAddRecipe: (Recipe) -> Unit,
    navController: NavController
) {
    val recipe = Recipe("", "", listOf())
    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            RecipeForm(
                "Aggiungi una ricetta", recipe, onAddRecipe, navController
            )
        }
    }
}
