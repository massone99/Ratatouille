import android.app.Application
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

    fun editRecipe(recipe: Recipe) {
        val index = recipes.indexOfFirst { it.name == recipe.name }
        if (index != -1) {
            recipes[index] = recipe
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
