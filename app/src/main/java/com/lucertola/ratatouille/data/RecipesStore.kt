package com.lucertola.ratatouille.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesStore(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("RecipesStore", Context.MODE_PRIVATE)

    fun saveRecipes(recipes: List<Recipe>) {
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(recipes)
        editor.putString("recipes", json)
        editor.apply()
    }

    fun getRecipes(): List<Recipe> {
        val json = sharedPreferences.getString("recipes", null)
        return if (json != null) {
            val type = object : TypeToken<List<Recipe>>() {}.type
            Gson().fromJson(json, type)
        } else {
            emptyList()
        }
    }
}
