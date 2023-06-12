package com.lucertola.ratatouille.ui.home

import HOME
import RecipeApp
import SHOPPING_PAGE
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController

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
