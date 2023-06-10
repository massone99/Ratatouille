package com.lucertola.ratatouille.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lucertola.ratatouille.data.Recipe
import com.lucertola.ratatouille.ui.theme.PastelYellowDark
import com.lucertola.ratatouille.ui.theme.PastelYellowLight


object RecipesList {
    /**
     * Returns a list of recipes.
     * @param recipes The list of recipes to display.
     * @param onViewRecipe The callback to invoke when a recipe is selected.
     */
    @Composable
    fun RecipesList(recipes: List<Recipe>, onViewRecipe: (Recipe) -> Unit) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun RecipeItem(recipe: Recipe, onViewRecipe: (Recipe) -> Unit) {
        val haptic = LocalHapticFeedback.current
        val isSelected = remember { mutableStateOf(false) }
        val color = if (isSelected.value) {
            // take the current color and make it slightly darker
            PastelYellowDark
        } else {
            PastelYellowLight
        }
        val shape = RoundedCornerShape(13.dp)
        Card(
            modifier = Modifier
                .clip(shape)
                .padding(8.dp)
                .combinedClickable(
                    onClick = {},
                    onLongClick = {
                        isSelected.value = !isSelected.value
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    },
                )
                .background(color, shape = shape),
            shape = shape,
        ) {
            Column(
                // make column of the same color as Card
                modifier = if (isSelected.value) Modifier
                    .background(color)
                    .width(300.dp) else Modifier.width(300.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(8.dp),
                )
                Text(
                    text = recipe.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.dp),
                )
                Text(
                    text = recipe.ingredientsToGrams.map { it.first }.joinToString(", "),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.dp),
                )

                Button(onClick = { onViewRecipe(recipe) }, modifier = Modifier.padding(8.dp)) {
                    Text("View Recipe")
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewRecipeItem() {
    RecipesList.RecipeItem(
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
