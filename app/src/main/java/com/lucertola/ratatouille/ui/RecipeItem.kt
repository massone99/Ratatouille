package com.lucertola.ratatouille.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import com.lucertola.ratatouille.data.Recipe
import com.lucertola.ratatouille.ui.theme.CardBackgroundLight

/**
 * Return a RecipeItem (a view representing a recipe).
 * @param recipe The recipe to display.
 * @param onViewRecipe The callback to invoke when the recipe is viewed.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipeItem(recipe: Recipe, onViewRecipe: (Recipe) -> Unit) {
    val haptic = LocalHapticFeedback.current
    val isSelected = remember { mutableStateOf(false) }
    val backgroundColor = if (isSelected.value) {
        MaterialTheme.colorScheme.primary
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
            )
            .background(backgroundColor, shape = shape),
        shape = shape,
        elevation = CardDefaults.elevatedCardElevation(4.dp),
    ) {
        Column(
            modifier = Modifier
                .background(backgroundColor)
                .width(300.dp)
                .padding(vertical = 16.dp, horizontal = 12.dp), // Adjust padding for better spacing
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            Text(
                recipe.ingredients.joinToString(separator = "\n") { "${it.name} - ${it.grams}gr" },
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            Button(
                onClick = { onViewRecipe(recipe) },
                modifier = Modifier
                    .fillMaxWidth() // Make the button full width
                    .height(48.dp), // Increase button height for better touch target
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = Color.Black,
                )
            ) {
                Text("Vedi Ricetta", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
