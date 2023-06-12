import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lucertola.ratatouille.ui.ShoppingPage.ShoppingPage
import com.lucertola.ratatouille.ui.home.HomeBottomAppBar
import com.lucertola.ratatouille.ui.home.HomeTopAppBar
import com.lucertola.ratatouille.ui.pages.AddRecipePage
import com.lucertola.ratatouille.ui.pages.EditRecipePage
import com.lucertola.ratatouille.ui.pages.RecipesList
import com.lucertola.ratatouille.ui.pages.ViewRecipePage

const val HOME = "RecipesListPage"
const val VIEW_RECIPE = "ViewRecipePage"
const val ADD_RECIPE = "AddRecipePage"
const val EDIT_RECIPE = "EditRecipePage"
const val SHOPPING_PAGE = "ShoppingPage"

object RecipeApp {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun RecipeApp(recipeViewModel: RecipeViewModel) {
        val navController = rememberNavController()

        Scaffold(topBar = {
            HomeTopAppBar(navController)
        }, bottomBar = {
            val backgroundColor = if (isSystemInDarkTheme()) {
                Color.Black // Set dark theme color
            } else {
                Color.White // Set light theme color
            }

            val contentColor = if (isSystemInDarkTheme()) {
                Color.White // Set dark theme color
            } else {
                Color.Black // Set light theme color
            }

            HomeBottomAppBar(backgroundColor, contentColor, navController)
        }) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxWidth(1f)
            ) {
                NavHost(navController = navController, startDestination = HOME) {
                    composable(HOME) {
                        RecipesList(recipeViewModel.recipes) { recipe ->
                            recipeViewModel.selectedRecipe.value = recipe
                            navController.navigate(VIEW_RECIPE)
                        }
                    }
                    composable(VIEW_RECIPE) {
                        recipeViewModel.selectedRecipe.value?.let { recipe ->
                            ViewRecipePage(recipe, {
                                recipeViewModel.removeRecipe(recipe)
                                navController.navigate(HOME)
                            }) {
                                navController.navigate(EDIT_RECIPE)
                            }
                        }
                    }
                    composable(ADD_RECIPE) {
                        AddRecipePage(
                            recipeViewModel::addRecipe,
                            navController
                        )
                    }
                    composable(EDIT_RECIPE) {
                        recipeViewModel.selectedRecipe.value?.let { recipe ->
                            EditRecipePage(recipe, {
                                Log.d("RecipeApp", "EditRecipeIngredient: ${it.ingredients}")
                                recipeViewModel.editRecipe(it.id, it)
                                navController.popBackStack()
                            }, navController)
                        }
                    }
                    composable(SHOPPING_PAGE) {
                        ShoppingPage(recipes = recipeViewModel.shoppingRecipes)
                    }
                }
            }
        }
    }

    @Composable
    fun currentRoute(navController: NavHostController): String? {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        return navBackStackEntry?.destination?.route
    }
}
