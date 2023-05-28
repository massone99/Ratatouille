import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.lucertola.ratatouille.data.Recipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewRecipePage(
    recipe: Recipe,
    onDismissRequest: () -> Unit,
    onDeleteRecipe: (Recipe) -> Unit,
    onEditRecipe: (Recipe) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(recipe.name) },
                navigationIcon = {
                    IconButton(onClick = onDismissRequest) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { Column(modifier = Modifier.padding(it)) {
            Text("Descrizione:")
            Text(recipe.description)
            Text("\n")
            Text("Ingredienti:")
            Text(recipe.ingredients.joinToString(", "))

            Button(onClick = { onEditRecipe(recipe) }) {
                Text("Edit")
            }
            Button(onClick = { onDeleteRecipe(recipe) }) {
                Text("Delete")
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipePage(onAddRecipe: (Recipe) -> Unit, onDismissRequest: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add a new recipe") },
                navigationIcon = {
                    IconButton(onClick = onDismissRequest) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
            OutlinedTextField(value = ingredients, onValueChange = { ingredients = it }, label = { Text("Ingredients") })

            Button(onClick = {
                onAddRecipe(Recipe(name, description, ingredients.split(",")))
            }) {
                Text("Add")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRecipePage(
    recipe: Recipe,
    onEditRecipe: (Recipe) -> Unit,
    onDismissRequest: () -> Unit
) {
    var name by remember { mutableStateOf(recipe.name) }
    var description by remember { mutableStateOf(recipe.description) }
    var ingredients by remember { mutableStateOf(recipe.ingredients.joinToString(",")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit recipe") },
                navigationIcon = {
                    IconButton(onClick = onDismissRequest) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
            OutlinedTextField(value = ingredients, onValueChange = { ingredients = it }, label = { Text("Ingredients") })

            Button(onClick = {
                onEditRecipe(Recipe(name, description, ingredients.split(",")))
            }) {
                Text("Edit")
            }
        }
    }
}
