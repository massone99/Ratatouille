import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.lucertola.ratatouille.data.Recipe
import com.lucertola.ratatouille.data.RecipesStore

class RecipeViewModel(
    Application: Application
) : ViewModel() {
    private val recipesStore = RecipesStore(
        Application.applicationContext
    )

    val recipes = mutableStateListOf<Recipe>()
    val selectedRecipe = mutableStateOf<Recipe?>(null)
    val shoppingRecipes = mutableStateListOf<Recipe>()

    init {
        recipes.addAll(recipesStore.getRecipes())
    }

    fun addRecipe(recipe: Recipe) {
        recipes.add(recipe)
        recipesStore.saveRecipes(recipes)
    }

    fun removeRecipe(recipe: Recipe) {
        recipes.remove(recipe)
        recipesStore.saveRecipes(recipes)
        if (selectedRecipe.value == recipe) {
            selectedRecipe.value = null
        }
    }

    fun editRecipe(recipeId: String, editedRecipe: Recipe) {
        // retrieve the recipe with the given id
        recipes.find { it.id == editedRecipe.id }?.let { recipe ->
            recipe.name = editedRecipe.name
            recipe.description = editedRecipe.description
            Log.d("RecipeViewModel", "EditRecipeIngredient: ${editedRecipe.ingredients}")
            recipe.ingredients = editedRecipe.ingredients
            recipesStore.saveRecipes(recipes)
        }
    }

    fun addToShoppingList(recipe: Recipe) {
        if (!shoppingRecipes.contains(recipe)) {
            shoppingRecipes.add(recipe)
        }
    }

    fun removeFromShoppingList(recipe: Recipe) {
        shoppingRecipes.remove(recipe)
    }
}
