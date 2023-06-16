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
import com.lucertola.ratatouille.ui.home.HomeBottomAppBar
import com.lucertola.ratatouille.ui.home.HomeTopAppBar.HomeTopAppBar
import com.lucertola.ratatouille.ui.pages.AddRecipePage
import com.lucertola.ratatouille.ui.pages.EditRecipePage
import com.lucertola.ratatouille.ui.pages.RecipesListPage
import com.lucertola.ratatouille.ui.pages.ShoppingPage.ShoppingPage
import com.lucertola.ratatouille.ui.pages.ViewRecipePage.ViewRecipePage

const val HOME = "RecipesListPage"
const val VIEW_RECIPE = "ViewRecipePage"
const val ADD_RECIPE = "AddRecipePage"
const val EDIT_RECIPE = "EditRecipePage"
const val SHOPPING_PAGE = "ShoppingPage"

object RecipeApp {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun RecipeApp(ratatouilleViewModel: RatatouilleViewModel) {
        val navController = rememberNavController()

        Scaffold(topBar = {
            HomeTopAppBar(navController, ratatouilleViewModel)
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
                        RecipesListPage(
                            ratatouilleViewModel.recipes, viewModel = ratatouilleViewModel
                        ) { recipe ->
                            ratatouilleViewModel.selectedRecipe.value = recipe
                            navController.navigate(VIEW_RECIPE)
                        }
                    }
                    composable(VIEW_RECIPE) {
                        ratatouilleViewModel.selectedRecipe.value?.let { recipe ->
                            ViewRecipePage(recipe, {
                                ratatouilleViewModel.removeRecipe(recipe)
                                navController.navigate(HOME)
                            }) {
                                navController.navigate(EDIT_RECIPE)
                            }
                        }
                    }
                    composable(ADD_RECIPE) {
                        AddRecipePage(
                            {
                                ratatouilleViewModel.addRecipe(it)
                                navController.navigate(HOME)
                            }, navController
                        )
                    }
                    composable(EDIT_RECIPE) {
                        ratatouilleViewModel.selectedRecipe.value?.let { recipe ->
                            EditRecipePage(recipe, {
                                Log.d("RecipeApp", "EditRecipeIngredient: ${it.ingredients}")
                                ratatouilleViewModel.editRecipe(it)
                                navController.popBackStack()
                            }, navController)
                        }
                    }
                    composable(SHOPPING_PAGE) {
                        ShoppingPage(ratatouilleViewModel.shoppingIngredients, ratatouilleViewModel)
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
