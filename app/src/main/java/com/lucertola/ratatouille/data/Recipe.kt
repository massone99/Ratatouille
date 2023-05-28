package com.lucertola.ratatouille.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(var name: String, val description: String, val ingredients: List<String>) :
    Parcelable
