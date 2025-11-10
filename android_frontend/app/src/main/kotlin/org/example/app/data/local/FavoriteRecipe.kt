package org.example.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Local Room entity to persist favorite recipe IDs.
 */
@Entity(tableName = "favorite_recipes")
data class FavoriteRecipe(
    @PrimaryKey val id: String,
    val title: String,
    val imageUrl: String?
)
