package com.lucertola.ratatouille.ui.ingredients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
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
    onAddClick: () -> Unit,
    onDeleteClick: () -> Unit,
    isLast: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            value = ingredientToRender.name,
            onValueChange = { newName -> onNameChange(newName) },
            label = {
                Text(
                    "Ingrediente",
                    style = MaterialTheme.typography.bodySmall,
                    color = LocalContentColor.current.copy(LocalContentAlpha.current)
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
        )
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp, end = 8.dp),
            value = ingredientToRender.grams,
            onValueChange = { newGrams -> onGramsChange(newGrams) },
            label = {
                Text(
                    "Grammi",
                    style = MaterialTheme.typography.bodySmall,
                    color = LocalContentColor.current.copy(LocalContentAlpha.current)
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        // Add button will be displayed only on the last row
        val iconSize = 24.dp
        if (isLast) {
            IconButton(onClick = onAddClick, modifier = Modifier.padding(start = 8.dp)) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Aggiungi ingrediente",
                    modifier = Modifier.size(iconSize)
                )
            }
        }
        IconButton(onClick = onDeleteClick, modifier = Modifier.padding(start = 8.dp)) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Cancella ingrediente",
                modifier = Modifier.size(iconSize)
            )
        }
    }
}
