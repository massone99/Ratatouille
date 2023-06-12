package com.lucertola.ratatouille.ui.components.ingredients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientRow(
    ingredientName: String,
    ingredientGrams: String,
    onIngredientChange: (String, String) -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(modifier = Modifier.weight(1f),
            value = ingredientName,
            onValueChange = { newName -> onIngredientChange(newName, ingredientGrams) },
            label = { Text("Ingrediente") })
        Spacer(Modifier.width(8.dp))
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = ingredientGrams,
            onValueChange = { newGrams -> onIngredientChange(ingredientName, newGrams) },
            label = { Text("Grammi") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        IconButton(onClick = onDeleteClick) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete ingredient")
        }
    }
}