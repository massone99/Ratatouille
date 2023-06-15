package com.lucertola.ratatouille.ui.home

import HOME
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
import androidx.navigation.compose.currentBackStackEntryAsState
import com.lucertola.ratatouille.ui.theme.ButtonBackgroundLight

enum class BottomNavItem(val route: String) {
    Home(HOME),
    Shopping(SHOPPING_PAGE)
}

@Composable
fun HomeBottomAppBar(
    backgroundColor: Color, contentColor: Color, navController: NavHostController
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val selectedColor = ButtonBackgroundLight
    val unselectedColor = Color.Gray

    BottomNavigation(
        backgroundColor = backgroundColor, contentColor = contentColor
    ) {
        BottomNavItem.values().forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        when (screen) {
                            BottomNavItem.Home -> Icons.Filled.Home
                            BottomNavItem.Shopping -> Icons.Filled.ShoppingCart
                        },
                        contentDescription = screen.name,
                        tint = if (currentRoute == screen.route) selectedColor else unselectedColor
                    )
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(screen.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
