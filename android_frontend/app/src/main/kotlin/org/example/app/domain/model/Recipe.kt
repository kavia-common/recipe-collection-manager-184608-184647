package org.example.app.domain.model

/**
 * PUBLIC_INTERFACE
 * Data class representing a recipe displayed in the app.
 */
data class Recipe(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val servings: Int,
    val timeMinutes: Int,
    val ingredients: List<String>,
    val instructions: List<String>
)
