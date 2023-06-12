package com.lucertola.ratatouille.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lucertola.ratatouille.data.Ingredient
import com.lucertola.ratatouille.data.Recipe
import com.lucertola.ratatouille.ui.theme.CardBackgroundLight


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
        CardBackgroundLight
    } else {
        CardBackgroundLight
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
            ),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
            containerColor = CardBackgroundLight,
        ),
        elevation = CardDefaults.elevatedCardElevation(),
        shape = shape,
    ) {
        Column(
            modifier = if (isSelected.value) Modifier
                .background(color)
                .fillMaxWidth() else Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(8.dp),
            )
            val emptyIngredients = recipe.ingredients.isEmpty()
            Text(
                if (emptyIngredients) "Nessun ingrediente specificato"
                else recipe.ingredients.joinToString(separator = "\n") { "${it.name} - ${it.grams}gr" },
                style = MaterialTheme.typography.bodySmall,
                fontStyle = if (emptyIngredients) FontStyle.Italic else FontStyle.Normal,
            )
            Button(
                onClick = { onViewRecipe(recipe) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = Color.Black,
                ),
                modifier = Modifier.padding(8.dp)
            ) {
                Text("View Recipe")
            }
        }
    }
}

@Composable
@Preview
fun PreviewRecipeItem() {
    RecipeItem(recipe = Recipe(
        name = "Pasta al pomodoro", description = "Pasta al pomodoro", ingredients = listOf(
            Ingredient(name = "Pasta", grams = "100"),
        )
    ), onViewRecipe = {})
}
