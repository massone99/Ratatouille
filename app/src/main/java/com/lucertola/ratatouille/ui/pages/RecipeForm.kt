package com.lucertola.ratatouille.ui.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lucertola.ratatouille.data.Recipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeForm(
    title: String, // title to be shown on the form
    recipe: Recipe, // the recipe data to prepopulate the form
    onFormResult: (Recipe) -> Unit, // callback when the form is submitted
    navController: NavController // navigation controller for screen transitions
) {
    // State variables for the recipe name and description
    var name by remember { mutableStateOf(recipe.name) }
    var description by remember { mutableStateOf(recipe.description) }

    // State variable for the list of ingredients.
    // Each ingredient is itself a mutable state to allow for independent updates.
    var ingredientsFields by remember {
        mutableStateOf(recipe.ingredientsToGrams.map {
            mutableStateOf(
                it
            )
        })
    }
    // State variables for the input fields for a new ingredient
    var ingredientName by remember { mutableStateOf("") }
    var ingredientGrams by remember { mutableStateOf("") }

    // The layout structure of the form
    Column(
        modifier = Modifier
            .fillMaxWidth() // fills the entire width of the parent
            .padding(16.dp) // adds padding around the form
            .verticalScroll(enabled = true, state = rememberScrollState()) // makes the form scrollable
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth() // fills the entire width of the parent
                .padding(8.dp) // adds padding around the card
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp) // adds vertical spacing between the children of this column
            ) {
                // Various UI elements of the form
                Text(
                    title, style = MaterialTheme.typography.headlineMedium
                )
                OutlinedTextField(modifier = Modifier.fillMaxWidth(), // a text field that fills the entire width of the parent
                    value = name,
                    onValueChange = { name = it }, // updates the state variable when the user types into the field
                    label = { Text("Nome") }) // label for the text field
                OutlinedTextField(modifier = Modifier.fillMaxWidth(), // a text field that fills the entire width of the parent
                    value = description,
                    onValueChange = { description = it }, // updates the state variable when the user types into the field
                    label = { Text("Descrizione") }) // label for the text field

                // Loop over the ingredients list and create a row for each ingredient
                ingredientsFields.forEachIndexed { index, ingredientField ->
                    val (innerIngredientName, innerIngredientGrams) = ingredientField.value
                    IngredientRow(ingredientName = innerIngredientName,
                        ingredientGrams = innerIngredientGrams,
                        onIngredientChange = { newName, newGrams ->
                            // update the ingredient when the user types into the field
                            ingredientField.value = newName to newGrams
                        },
                        onDeleteClick = {
                            // remove the ingredient from the list when the user clicks the delete button
                            ingredientsFields =
                                ingredientsFields.filterIndexed { i, _ -> i != index }
                        })
                }

                // An input row for adding a new ingredient
                IngredientInputRow(ingredientName = ingredientName,
                    ingredientGrams = ingredientGrams,
                    onIngredientChange = { newName, newGrams ->
                        // update the state variables when the user types into the field
                        ingredientName = newName
                        ingredientGrams = newGrams
                    },
                    onAddClick = {
                        // add the new ingredient to the list when the user clicks the add button
                        ingredientsFields += mutableStateOf(ingredientName to ingredientGrams)
                        ingredientName = ""
                        ingredientGrams = ""
                    })

                // A row of buttons at the bottom of the form
                Row(
                    modifier = Modifier.fillMaxWidth(), // fills the entire width of the parent
                    horizontalArrangement = Arrangement.End, // aligns the buttons to the end of the row
                    verticalAlignment = Alignment.CenterVertically // centers the buttons vertically in the row
                ) {
                    // The confirm button
                    Button(onClick = {
                        // create a new recipe object from the state variables and call the onFormResult callback when the button is clicked
                        val ingredientsToGrams = ingredientsFields.map { it.value }
                        onFormResult(
                            Recipe(
                                name, description, ingredientsToGrams
                            )
                        )
                    }) {
                        Text("Confirm")
                    }
                    Spacer(Modifier.width(16.dp)) // adds horizontal space between the buttons
                    // The cancel button
                    Button(onClick = { navController.navigateUp() }) { // navigates to the previous screen when the button is clicked
                        Text("Cancel")
                    }
                }
            }
        }
    }
}
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientInputRow(
    ingredientName: String,
    ingredientGrams: String,
    onIngredientChange: (String, String) -> Unit,
    onAddClick: () -> Unit
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
        IconButton(onClick = onAddClick) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add ingredient")
        }
    }
}

object EditRecipePage {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun EditRecipePage(
        recipe: Recipe, navController: NavController, onEditRecipe: (Recipe) -> Unit
    ) {
        Scaffold {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxWidth()
            ) {
                RecipeForm("Modifica la ricetta", recipe, onEditRecipe, navController)
            }
        }
    }
}

object AddRecipePage {
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
}
