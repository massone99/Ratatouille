package com.lucertola.ratatouille.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.lucertola.ratatouille.data.Recipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipePage(navController: NavController, onAddRecipe: (Recipe) -> Unit) {
    val recipe = Recipe("", "", listOf())
    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            RecipeForm("Aggiungi una ricetta", recipe, onAddRecipe, navController)
        }
    }
}
