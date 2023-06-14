package com.lucertola.ratatouille.ui.components

import RatatouilleViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lucertola.ratatouille.data.Recipe
import com.lucertola.ratatouille.ui.theme.ButtonBackgroundDark

object ShoppingDialog {
    @Composable
    fun ShoppingDialog(
        showDialog: MutableState<Boolean>,
        cardBackgroundColor: Color,
        recipe: Recipe,
        viewModel: RatatouilleViewModel
    ) {
        var quantity by remember { mutableStateOf("0") }
        AlertDialog(onDismissRequest = { showDialog.value = false }, title = {
            Text(
                text = "Compra ingredienti ricetta",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
        }, text = {
            Column {
                Text(text = "Seleziona quante volte vuoi preparare la ricetta")
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = quantity,
                    onValueChange = {
                        quantity = it
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = ButtonBackgroundDark,
                        cursorColor = ButtonBackgroundDark,
                    ),
                )
            }
        }, containerColor = cardBackgroundColor, confirmButton = {
            Button(
                onClick = {
                    // Retrieve all the ingredients of the recipe
                    val ingredients = recipe.ingredients
                    // Create a list with the ingredients multiplied by the quantity
                    val ingredientsToBuy = ingredients.map { ingredient ->
                        if (quantity.isNotBlank() && ingredient.grams.isNotBlank()) {
                            val grams = (ingredient.grams.toInt() * quantity.toInt()).toString()
                            ingredient.copy(grams = grams)
                        } else {
                            ingredient
                        }
                    }
                    viewModel.addShoppingIngredients(ingredientsToBuy)
                    showDialog.value = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = Color.Black,
                ),
            ) {
                Text(text = "Aggiungi")
            }
        }, dismissButton = {
            Button(
                onClick = {
                    showDialog.value = false
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = Color.Black,
                )
            ) {
                Text(text = "Annulla")
            }
        })
    }
}