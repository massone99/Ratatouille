import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.lucertola.ratatouille.data.Ingredient
import com.lucertola.ratatouille.data.Recipe
import com.lucertola.ratatouille.data.store.RecipesStore
import com.lucertola.ratatouille.data.store.ShoppingIngredientsStore

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

    init {
        recipes.addAll(recipesStore.getRecipes())
        shoppingIngredients.addAll(shoppingIngredientsStore.getShoppingIngredients())
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

    fun editRecipe(editedRecipe: Recipe) {
        // retrieve the recipe with the given id
        recipes.find { it.id == editedRecipe.id }?.let { recipe ->
            recipe.name = editedRecipe.name
            recipe.description = editedRecipe.description
            Log.d("RecipeViewModel", "EditRecipeIngredient: ${editedRecipe.ingredients}")
            recipe.ingredients = editedRecipe.ingredients
            recipesStore.saveRecipes(recipes)
        }
    }

    fun addShoppingIngredients(ingredients: List<Ingredient>) {
        // if an ingredient is already in the shopping list, we don't add it again but only add the grams
        ingredients.forEach { ingredient ->
            val existingIngredient = shoppingIngredients.find { it.name == ingredient.name }
            if (existingIngredient?.grams != null) {
                existingIngredient.grams =
                    (ingredient.grams.toInt() + existingIngredient.grams.toInt()).toString()
            } else {
                shoppingIngredients.add(ingredient)
            }
        }
        shoppingIngredientsStore.saveShoppingIngredients(shoppingIngredients)
    }

    fun removeIngredientFromShoppingList(ingredient: Ingredient) {
        shoppingIngredients.remove(ingredient)
        shoppingIngredientsStore.saveShoppingIngredients(shoppingIngredients)
    }
}
