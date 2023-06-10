package com.lucertola.ratatouille.data

data class Recipe(var name: String, val description: String, val ingredientsToGrams: List<Pair<String, String>>)