package com.lucertola.ratatouille.ui.home

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.lucertola.ratatouille.ui.HOME
import com.lucertola.ratatouille.ui.RecipeApp
import com.lucertola.ratatouille.ui.SHOPPING_PAGE

@Composable
fun HomeBottomAppBar(
    backgroundColor: Color,
    contentColor: Color,
    navController: NavHostController
) {
    BottomNavigation(
        backgroundColor = backgroundColor, contentColor = contentColor
    ) {
        // by default the current route is HOME
        val currentRoute = RecipeApp.currentRoute(navController)
        BottomNavigationItem(icon = {
            Icon(
                Icons.Filled.Home, contentDescription = "Home"
            )
        }, selected = currentRoute == HOME, onClick = { navController.navigate(HOME) })
        BottomNavigationItem(icon = {
            Icon(
                Icons.Filled.ShoppingCart, contentDescription = "Shopping"
            )
        },
            selected = currentRoute == SHOPPING_PAGE,
            onClick = { navController.navigate(SHOPPING_PAGE) })
    }
}
