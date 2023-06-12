package com.lucertola.ratatouille.ui.ingredients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.lucertola.ratatouille.data.Ingredient

/**
 * A Composable that represents a row of a form where the user can add a new ingredient.
 * @param ingredientToRender the ingredient to be added
 * @param onNameChange the callback to be called when the ingredient name changes
 * @param onGramsChange the callback to be called when the ingredient grams change
 * @param onAddClick the callback to be called when the user clicks on the add button
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PendingIngredientRow(
    ingredientToRender: Ingredient,
    onNameChange: (String) -> Unit,
    onGramsChange: (String) -> Unit,
    onAddClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(modifier = Modifier.weight(1f),
            value = ingredientToRender.name,
            onValueChange = { newName -> onNameChange(newName) },
            label = { Text("Ingrediente") })
        Spacer(Modifier.width(8.dp))
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = ingredientToRender.grams,
            onValueChange = { newGrams -> onGramsChange(newGrams) },
            label = { Text("Grammi") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        // we add another IngredientInputRow when the user clicks on the add button
        IconButton(onClick = onAddClick) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add ingredient")
        }
    }
}
