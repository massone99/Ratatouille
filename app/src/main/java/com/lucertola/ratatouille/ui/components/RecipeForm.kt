package com.lucertola.ratatouille.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lucertola.ratatouille.data.Ingredient
import com.lucertola.ratatouille.data.Recipe
import com.lucertola.ratatouille.ui.ingredients.PendingIngredientRow
import com.lucertola.ratatouille.ui.theme.ButtonBackgroundDark
import com.lucertola.ratatouille.ui.theme.ButtonBackgroundLight
import com.lucertola.ratatouille.ui.theme.CardBackgroundDark
import com.lucertola.ratatouille.ui.theme.CardBackgroundLight

@Composable
fun RecipeForm(
    title: String, // title to be shown on the form
    recipe: Recipe, // the recipe data to prepopulate the form
    onFormResult: (Recipe) -> Unit, // callback when the form is submitted
    navController: NavController
) {
    var name by remember { mutableStateOf(recipe.name) }
    var description by remember { mutableStateOf(recipe.description) }
    var ingredients by remember { mutableStateOf(recipe.ingredients) }

    if (ingredients.isEmpty()) {
        ingredients = ingredients + Ingredient("", "")
    }

    BackHandler(
        onBack = confirmEvent(
            name, description, ingredients, recipe, onFormResult
        )
    )

    RecipeFormLayout(
        title = title,
        name = name,
        onNameChange = { name = it },
        description = description,
        onDescriptionChange = { description = it },
        ingredients = ingredients,
        onIngredientsChange = { ingredients = it },
        onFormResult = onFormResult,
        recipe = recipe,
        navController = navController
    )
}

@Composable
fun RecipeFormLayout(
    title: String,
    name: String,
    onNameChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    ingredients: List<Ingredient>,
    onIngredientsChange: (List<Ingredient>) -> Unit,
    onFormResult: (Recipe) -> Unit,
    recipe: Recipe,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(
                enabled = true, state = rememberScrollState()
            )
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(), // fills the entire width of the parent
            colors = CardDefaults.cardColors(
                contentColor = MaterialTheme.colorScheme.onSurface,
                containerColor = if (isSystemInDarkTheme()) {
                    CardBackgroundDark
                } else {
                    CardBackgroundLight
                }
            ),
            elevation = CardDefaults.elevatedCardElevation(4.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp) // adds vertical spacing between the children of this column
            ) {
                TitleText(title)
                NameField(name, onNameChange)
                DescriptionField(description, onDescriptionChange)
                IngredientList(ingredients, onIngredientsChange)
                ConfirmationButtons(onConfirmClick = confirmEvent(
                    name, description, ingredients, recipe, onFormResult
                ), onCancelClick = { navController.popBackStack() })
            }
        }
    }
}

@Composable
fun TitleText(title: String) {
    Text(
        title,
        style = MaterialTheme.typography.headlineMedium,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
        label = { Text("Nome") },
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescriptionField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
        onValueChange = onValueChange,
        label = { Text("Descrizione") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun IngredientList(ingredients: List<Ingredient>, onIngredientsChange: (List<Ingredient>) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ingredients.forEachIndexed { idxUpdatedIngredient, ingredient ->
            var ingrName by remember { mutableStateOf(ingredient.name) }
            var ingrGrams by remember { mutableStateOf(ingredient.grams) }

            PendingIngredientRow(ingredientToRender = ingredient, onNameChange = { newIngrName ->
                ingrName = newIngrName
                onIngredientsChange(ingredients.mapIndexed { idx, ingr ->
                    if (idx == idxUpdatedIngredient) ingr.copy(name = newIngrName) else ingr
                })
            }, onGramsChange = { newIngrGrams ->
                ingrGrams = newIngrGrams
                onIngredientsChange(ingredients.mapIndexed { idx, ingr ->
                    if (idx == idxUpdatedIngredient) ingr.copy(grams = newIngrGrams) else ingr
                })
            }, onAddClick = {
                onIngredientsChange(ingredients + Ingredient("", ""))
            }, onDeleteClick = {
                onIngredientsChange(ingredients.filterIndexed { idx, _ -> idx != idxUpdatedIngredient })
            }, isLast = idxUpdatedIngredient == ingredients.lastIndex
            )
        }
    }
}

@Composable
fun ConfirmationButtons(onConfirmClick: () -> Unit, onCancelClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = onConfirmClick, colors = ButtonDefaults.buttonColors(
            containerColor = if (isSystemInDarkTheme()) {
                ButtonBackgroundDark
            } else {
                ButtonBackgroundLight
            },
            contentColor = Color.Black,
        ), content = {
            Text(text = "Conferma", color = Color.Black)
        })
        Button(onClick = onCancelClick, colors = ButtonDefaults.buttonColors(
            containerColor = if (isSystemInDarkTheme()) {
                ButtonBackgroundDark
            } else {
                ButtonBackgroundLight
            },
            contentColor = Color.Black,
        ), content = {
            Text(text = "Annulla", color = Color.Black)
        })
    }
}

@Composable
private fun confirmEvent(
    name: String,
    description: String,
    ingredients: List<Ingredient>,
    recipe: Recipe,
    onFormResult: (Recipe) -> Unit
): () -> Unit = {
    val editedRecipe = Recipe(name, description, ingredients)
    editedRecipe.id = recipe.id
    onFormResult(editedRecipe)
}
