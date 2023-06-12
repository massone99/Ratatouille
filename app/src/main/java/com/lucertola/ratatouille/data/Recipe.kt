package com.lucertola.ratatouille.data

import java.util.UUID

class Recipe(
    var name: String,
    var description: String,
    var ingredients: List<Ingredient>
) {
    var id: String = UUID.randomUUID().toString()
}