package com.lucertola.ratatouille.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lucertola.ratatouille.data.Ingredient
import com.lucertola.ratatouille.data.Recipe
import com.lucertola.ratatouille.ui.ingredients.PendingIngredientRow
import com.lucertola.ratatouille.ui.theme.CardBackgroundLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeForm(
    title: String, // title to be shown on the form
    recipe: Recipe, // the recipe data to prepopulate the form
    onFormResult: (Recipe) -> Unit, // callback when the form is submitted
    navController: NavController
) {

    var name by remember {
        mutableStateOf(recipe.name)
    }
    var description by remember {
        mutableStateOf(recipe.description)
    }
    var ingredients by remember {
        mutableStateOf(recipe.ingredients)
    }

    if (ingredients.isEmpty()) {
        ingredients = ingredients + Ingredient("", "")
    }

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
                // Title
                Text(
                    title, style = MaterialTheme.typography.headlineMedium
                )
                // OutlineTextField for the recipe name
                OutlinedTextField(value = name, onValueChange = {
                    name = it
                }, label = { Text("Nome") }, modifier = Modifier.fillMaxWidth()
                )
                // OutlineTextField for the recipe description
                OutlinedTextField(value = description, onValueChange = {
                    description = it
                }, label = { Text("Descrizione") }, modifier = Modifier.fillMaxWidth()
                )
                // The list of ingredients
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // for each of the ingredients in the recipe create a PendingIngredientRow
                    ingredients.forEachIndexed { idxUpdatedIngredient, ingredient ->
                        var ingrName by remember { mutableStateOf(ingredient.name) }
                        var ingrGrams by remember { mutableStateOf(ingredient.grams) }

                        PendingIngredientRow(ingredientToRender = ingredient,
                            onNameChange = { newIngrName ->
                                ingrName = newIngrName
                                ingredients = ingredients.mapIndexed { idx, ingr ->
                                    // we propagate the change to the list of ingredients
                                    if (idx == idxUpdatedIngredient) ingr.copy(name = newIngrName) else ingr
                                }
                            },
                            onGramsChange = { newIngrGrams ->
                                ingrGrams = newIngrGrams
                                ingredients = ingredients.mapIndexed { idx, ingr ->
                                    // we propagate the change to the list of ingredients
                                    if (idx == idxUpdatedIngredient) ingr.copy(grams = newIngrGrams) else ingr
                                }
                            },
                            onAddClick = {
                                ingredients = ingredients + Ingredient("", "")
                            },
                            onDeleteClick = {
                                ingredients =
                                    ingredients.filterIndexed { idx, _ -> idx != idxUpdatedIngredient }
                            })
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = {
                            val editedRecipe = Recipe(name, description, ingredients)
                            editedRecipe.id = recipe.id
                            onFormResult(editedRecipe)
                        }, content = {
                            Text(text = "Conferma")
                        })
                        Button(onClick = {
                            navController.popBackStack()
                        }, content = {
                            Text(text = "Annulla")
                        })
                    }
                }
            }
        }
    }
}