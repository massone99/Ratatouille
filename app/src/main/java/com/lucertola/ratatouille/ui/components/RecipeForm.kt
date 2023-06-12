package com.lucertola.ratatouille.ui.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lucertola.ratatouille.data.Recipe
import com.lucertola.ratatouille.ui.ingredients.IngredientInputRow
import com.lucertola.ratatouille.ui.ingredients.IngredientRow
import com.lucertola.ratatouille.ui.theme.CardBackgroundLight

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
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(
                enabled = true, state = rememberScrollState()
            )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth() // fills the entire width of the parent
                .padding(8.dp), // adds padding around the card
            colors = CardDefaults.cardColors(
                contentColor = MaterialTheme.colorScheme.onSurface,
                containerColor = CardBackgroundLight,
            ),
            elevation = CardDefaults.elevatedCardElevation(4.dp),
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
                    value = name, onValueChange = {
                        name = it
                    }, // updates the state variable when the user types into the field
                    label = { Text("Nome") }) // label for the text field

                OutlinedTextField(modifier = Modifier.fillMaxWidth(), // a text field that fills the entire width of the parent
                    value = description, onValueChange = {
                        description = it
                    }, // updates the state variable when the user types into the field
                    label = { Text("Descrizione") }) // label for the text field

                // Loop over the ingredients list and create a row for each ingredient
                ingredientsFields = renderIngredients(ingredientsFields)

                // An input row for adding new ingredients
                IngredientInputRow(ingredientName = ingredientName,
                    ingredientGrams = ingredientGrams,
                    onIngredientChange = { newName, newGrams ->
                        // update the state variables when the user types into the field
                        ingredientName = newName
                        ingredientGrams = newGrams
                    },
                    onAddClick = {
                        // add the new ingredient to the list when the user clicks the add button
                        ingredientsFields =
                            ingredientsFields + mutableStateOf(ingredientName to ingredientGrams)
                        ingredientName = ""
                        ingredientGrams = ""
                    })

                // A row of buttons at the bottom of the form
                ButtonRow(ingredientsFields, onFormResult, name, description, navController)
            }
        }
    }
}

@Composable
private fun ButtonRow(
    ingredientsFields: List<MutableState<Pair<String, String>>>,
    onFormResult: (Recipe) -> Unit,
    name: String,
    description: String,
    navController: NavController
) {
    Row(
        modifier = Modifier.fillMaxWidth(), // fills the entire width of the parent
        horizontalArrangement = Arrangement.End, // aligns the buttons to the end of the row
        verticalAlignment = Alignment.CenterVertically // centers the buttons vertically in the row
    ) {
        // The confirm button
        Button(
            onClick = {
                // create a new recipe object from the state variables and call the onFormResult callback when the button is clicked
                val ingredientsToGrams = ingredientsFields.map { it.value }
                onFormResult(
                    Recipe(
                        name, description, ingredientsToGrams
                    )
                )
            }, colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = Color.Black,
            )
        ) {
            Text("Conferma")
        }
        Spacer(Modifier.width(16.dp)) // adds horizontal space between the buttons
        // The cancel button
        Button(
            onClick = { navController.navigateUp() }, colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = Color.Black,
            )
        ) { // navigates to the previous screen when the button is clicked
            Text("Cancella")
        }
    }
}

@Composable
private fun renderIngredients(ingredientsFields: List<MutableState<Pair<String, String>>>): List<MutableState<Pair<String, String>>> {
    var ingredientsFields1 = ingredientsFields
    ingredientsFields1.forEachIndexed { index, ingredientField ->
        val (innerIngredientName, innerIngredientGrams) = ingredientField.value
        IngredientRow(ingredientName = innerIngredientName,
            ingredientGrams = innerIngredientGrams,
            onIngredientChange = { newName, newGrams ->
                // update the ingredient when the user types into the field
                ingredientField.value = newName to newGrams
            },
            onDeleteClick = {
                // remove the ingredient from the list when the user clicks the delete button
                ingredientsFields1 = ingredientsFields1.filterIndexed { i, _ -> i != index }
            })
    }
    return ingredientsFields1
}