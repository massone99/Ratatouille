package com.lucertola.ratatouille.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lucertola.ratatouille.data.Recipe

object ShoppingPage {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ShoppingPage(recipes: List<Recipe>) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Shopping List") })
            },
            content = {
                Column(modifier = Modifier.padding(it)) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                    ) {
                        if (recipes.isNotEmpty()) {
                            LazyColumn(modifier = Modifier.weight(1f)) {
                                items(recipes.size) { index ->
                                    ShoppingItem(recipe = recipes[index])
                                    Divider()
                                }
                            }
                        } else {
                            Text(
                                text = "Nessuna ricetta da comprare!",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(vertical = 16.dp)
                            )
                        }
                        Button(
                            onClick = { /* Handle shopping action */ },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text(text = "Go Shopping")
                        }
                    }
                }
            }
        )
    }

    @Composable
    fun ShoppingItem(recipe: Recipe) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = false, onCheckedChange = { /* Handle item selection */ })
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = recipe.name)
        }
    }
}
