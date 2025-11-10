package org.example.app.data.local

/**
 * PUBLIC_INTERFACE
 * Lightweight data class representing a favorited recipe.
 * This no longer uses Room annotations; storage is handled by FavoriteStore.
 */
data class FavoriteRecipe(
    val id: String,
    val title: String,
    val imageUrl: String?
)
