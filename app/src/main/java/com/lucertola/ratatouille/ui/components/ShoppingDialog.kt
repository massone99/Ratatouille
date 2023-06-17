package com.lucertola.ratatouille.ui.components

import RatatouilleViewModel
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lucertola.ratatouille.R
import com.lucertola.ratatouille.data.Recipe
import com.lucertola.ratatouille.ui.theme.ButtonBackgroundDark
import com.lucertola.ratatouille.ui.theme.ButtonBackgroundLight

object ShoppingDialog {
    @Composable
    fun ShoppingDialog(
        showDialog: MutableState<Boolean>,
        cardBackgroundColor: Color,
        recipe: Recipe,
        viewModel: RatatouilleViewModel,
    ) {
        val quantity = remember { mutableStateOf("1") }
        val textColor = if (isSystemInDarkTheme()) Color.Black else Color.White

        AlertDialog(onDismissRequest = { showDialog.value = false }, title = {
            DialogTitle()
        }, text = {
            DialogContent(quantity, textColor)
        }, containerColor = cardBackgroundColor, confirmButton = {
            ConfirmButton(quantity, recipe, viewModel, showDialog, textColor)
        }, dismissButton = {
            DismissButton(showDialog, textColor)
        })
    }

    @Composable
    fun DialogTitle() {
        Text(
            text = stringResource(R.string.shopping_dialog_title),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
    }

    @Composable
    fun DialogContent(quantity: MutableState<String>, textColor: Color) {
        Column {
            Text(text = "Seleziona quante volte vuoi preparare la ricetta")
            Spacer(modifier = Modifier.height(8.dp))
            val textFieldColor =
                if (isSystemInDarkTheme()) ButtonBackgroundDark else ButtonBackgroundLight
            OutlinedTextField(
                value = quantity.value,
                onValueChange = {
                    quantity.value = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = textFieldColor,
                    unfocusedBorderColor = textFieldColor,
                    cursorColor = textFieldColor,
                ),
            )
        }
    }

    @Composable
    fun ConfirmButton(
        quantity: MutableState<String>,
        recipe: Recipe,
        viewModel: RatatouilleViewModel,
        showDialog: MutableState<Boolean>,
        textColor: Color,
    ) {
        Button(
            onClick = {
                // Retrieve all the ingredients of the recipe
                val ingredients = recipe.ingredients
                // Create a list with the ingredients multiplied by the quantity
                val ingredientsToBuy = ingredients.map { ingredient ->
                    if (quantity.value.isNotBlank() && ingredient.grams.isNotBlank()) {
                        val grams = (ingredient.grams.toInt() * quantity.value.toInt()).toString()
                        ingredient.copy(grams = grams)
                    } else {
                        ingredient
                    }
                }
                Log.d("ShoppingDialog", "ingredientsToBuy: $ingredientsToBuy")

                viewModel.addShoppingIngredients(ingredientsToBuy)
                showDialog.value = false
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isSystemInDarkTheme()) ButtonBackgroundDark else ButtonBackgroundLight,
                contentColor = textColor,
            ),
        ) {
            Text(text = "Aggiungi")
        }
    }

    @Composable
    fun DismissButton(showDialog: MutableState<Boolean>, textColor: Color) {
        Button(
            onClick = {
                showDialog.value = false
            }, colors = ButtonDefaults.buttonColors(
                containerColor = if (isSystemInDarkTheme()) ButtonBackgroundDark else ButtonBackgroundLight,
                contentColor = textColor,
            )
        ) {
            Text(text = "Annulla")
        }
    }
}
