package com.lucertola.ratatouille.data

data class Ingredient(
    var name: String,
    var grams: String
) {
    // create a random id when the object is created
    var id: String = java.util.UUID.randomUUID().toString()

    override fun toString(): String {
        return "($id) $name $grams"
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other is Ingredient) {
            return other.id == id
        }
        return false
    }
}
