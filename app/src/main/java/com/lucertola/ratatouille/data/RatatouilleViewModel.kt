import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.lucertola.ratatouille.data.Ingredient
import com.lucertola.ratatouille.data.Recipe
import com.lucertola.ratatouille.data.store.RecipesStore
import com.lucertola.ratatouille.data.store.ShoppingIngredientsStore
import java.util.UUID

class RatatouilleViewModel(
    Application: Application
) : ViewModel() {
    private val recipesStore = RecipesStore(
        Application.applicationContext
    )

    private val shoppingIngredientsStore = ShoppingIngredientsStore(
        Application.applicationContext
    )

    val recipes = mutableStateListOf<Recipe>()
    val selectedRecipe = mutableStateOf<Recipe?>(null)
    val shoppingIngredients = mutableStateListOf<Ingredient>()

    val isSorted = mutableStateOf(false)
    private var lastSearchQuery = ""


    val isSearchBarVisible = mutableStateOf(false)

    init {
        recipes.addAll(recipesStore.getRecipes())
        shoppingIngredients.addAll(shoppingIngredientsStore.getShoppingIngredients())
    }

    fun addRecipe(recipe: Recipe) {
        recipe.ingredients.forEach { ingredient ->
            ingredient.name = ingredient.name.replace("\n", " ")
        }
        recipes.add(recipe)
        recipesStore.saveRecipes(recipes)
    }

    /**
     * Sets the search bar visibility
     */
    fun setSearchBarVisibility(isVisible: Boolean) {
        isSearchBarVisible.value = isVisible
    }

    /**
     * Shows recipes that contain the given name
     */
    fun search(name: String) {
        lastSearchQuery = name
        val searchResults = recipesStore.getRecipes().filter { recipe ->
            recipe.name.contains(name, ignoreCase = true)
        }
        recipes.clear()
        if (isSorted.value) {
            recipes.addAll(searchResults.sortedBy { it.name })
        } else {
            recipes.addAll(searchResults)
        }
    }

    fun removeRecipe(recipe: Recipe) {
        recipes.remove(recipe)
        recipesStore.saveRecipes(recipes)
        if (selectedRecipe.value == recipe) {
            selectedRecipe.value = null
        }
    }

    fun editRecipe(editedRecipe: Recipe) {
        // replace the \n with a space for all ingredients in the edited recipe
        editedRecipe.ingredients.forEach { ingredient ->
            ingredient.name = ingredient.name.replace("\n", " ")
        }

        recipes.find { it.id == editedRecipe.id }?.let { recipe ->
            recipe.name = editedRecipe.name
            recipe.description = editedRecipe.description
            Log.d("RecipeViewModel", "EditRecipeIngredient: ${editedRecipe.ingredients}")
            recipe.ingredients = editedRecipe.ingredients
            recipesStore.saveRecipes(recipes)
        }
    }

    fun addShoppingIngredients(shoppingIngredients: List<Ingredient>) {
        Log.d("RatatouilleViewModel", "addShoppingIngredients: $shoppingIngredients")

        shoppingIngredients.forEach { ingredientToAdd ->
            val existingIngredient = this.shoppingIngredients.find {
                haveSameNameAndGrams(it, ingredientToAdd)
            }
            if (existingIngredient != null && existingIngredient.grams.isNotEmpty() && ingredientToAdd.grams.isNotEmpty()) {
                existingIngredient.grams =
                    (ingredientToAdd.grams.toInt() + existingIngredient.grams.toInt()).toString()
            } else {
                // Create a new ingredient with a unique ID
                val newIngredient =
                    Ingredient(name = ingredientToAdd.name, grams = ingredientToAdd.grams)
                newIngredient.id = UUID.randomUUID().toString()
                this.shoppingIngredients.add(newIngredient)
            }
        }
        shoppingIngredientsStore.saveShoppingIngredients(this.shoppingIngredients)
    }

    private fun haveSameNameAndGrams(
        it: Ingredient, ingredientToAdd: Ingredient
    ) = it.name.lowercase() == ingredientToAdd.name.lowercase() && it.grams == ingredientToAdd.grams

    fun removeIngredientFromShoppingList(ingredient: Ingredient) {
        shoppingIngredients.remove(ingredient)
        shoppingIngredientsStore.saveShoppingIngredients(shoppingIngredients)
    }

    fun toggleSortRecipes() {
        isSorted.value = !isSorted.value
        // Reapply the last search to update the sort order
        search(lastSearchQuery)
    }
}
