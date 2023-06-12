package com.lucertola.ratatouille.data

data class Ingredient(
    var name: String,
    var grams: String
) {
    // create a random id when the object is created
    val id: String = java.util.UUID.randomUUID().toString()
}
