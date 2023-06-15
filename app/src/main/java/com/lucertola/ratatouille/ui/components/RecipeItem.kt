package com.lucertola.ratatouille.ui.components

import RatatouilleViewModel
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lucertola.ratatouille.data.Recipe
import com.lucertola.ratatouille.ui.components.ShoppingDialog.ShoppingDialog
import com.lucertola.ratatouille.ui.theme.ButtonBackgroundDark
import com.lucertola.ratatouille.ui.theme.ButtonBackgroundLight
import com.lucertola.ratatouille.ui.theme.CardBackgroundDark
import com.lucertola.ratatouille.ui.theme.CardBackgroundLight


object RecipeItem {
    /**
     * Returns a single recipe.
     * @param recipe The recipe to display.
     * @param onViewRecipe The callback to invoke when a recipe is selected.
     */
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun RecipeItem(
        recipe: Recipe, viewModel: RatatouilleViewModel, onViewRecipe: (Recipe) -> Unit
    ) {
        val haptic = LocalHapticFeedback.current
        val cardBackgroundColor = if (isSystemInDarkTheme()) {
            CardBackgroundDark
        } else {
            CardBackgroundLight
        }

        val shape = RoundedCornerShape(13.dp)

        // Add this line to manage the dialog state
        val showDialog = remember { mutableStateOf(false) }

        if (showDialog.value) {
            ShoppingDialog(
                showDialog, cardBackgroundColor, recipe, viewModel
            )
        }
        Card(
            modifier = Modifier
                .clip(shape)
                .padding(8.dp)
                .height(180.dp)
                .combinedClickable(
                    onClick = {
                        onViewRecipe(recipe)
                    },
                    onLongClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        showDialog.value = true
                    },
                ),
            colors = CardDefaults.cardColors(
                contentColor = MaterialTheme.colorScheme.onSurface,
                containerColor = cardBackgroundColor,
            ),
            elevation = CardDefaults.elevatedCardElevation(),
            shape = shape,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = recipe.name,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(8.dp),
                )
                val emptyIngredients = recipe.ingredients.isEmpty()
                Text(
                    if (emptyIngredients) "Nessun ingrediente specificato"
                    else recipe.ingredients.joinToString(separator = "\n") { "${it.name}  ${if (it.grams.isBlank()) "" else "-" + it.grams + "gr"}" },
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    fontStyle = if (emptyIngredients) FontStyle.Italic else FontStyle.Normal,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { onViewRecipe(recipe) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSystemInDarkTheme()) {
                            ButtonBackgroundDark
                        } else {
                            ButtonBackgroundLight
                        },
                        contentColor = Color.Black,
                    ),
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Apri")
                }
            }
        }
    }
}