package com.lucertola.ratatouille.data.store

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lucertola.ratatouille.data.Ingredient

class ShoppingIngredientsStore(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("RecipesStore", Context.MODE_PRIVATE)

    fun saveShoppingIngredients(shoppingIngredients: List<Ingredient>) {
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(shoppingIngredients)
        editor.putString("shoppingIngredients", json)
        editor.apply()
    }

    fun getShoppingIngredients(): List<Ingredient> {
        val json = sharedPreferences.getString("shoppingIngredients", null)
        return if (json != null) {
            val type = object : TypeToken<List<Ingredient>>() {}.type
            Gson().fromJson(json, type)
        } else {
            emptyList()
        }
    }
}
