package com.lucertola.ratatouille.model

import RecipeApp.RecipeApp
import RecipeViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.lucertola.ratatouille.ui.theme.RatatouilleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recipeViewModel = RecipeViewModel(application)
        setContent {
            RatatouilleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    RecipeApp(
                        recipeViewModel
                    )
                }
            }
        }
    }
}
