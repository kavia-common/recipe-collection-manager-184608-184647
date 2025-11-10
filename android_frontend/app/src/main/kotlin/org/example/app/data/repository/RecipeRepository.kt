package org.example.app.data.repository

import android.content.Context
import kotlinx.coroutines.delay
import org.example.app.domain.model.Recipe

/**
 * PUBLIC_INTERFACE
 * RecipeRepository provides access to local mock recipes and basic search.
 */
class RecipeRepository(context: Context) {

    // In a real app, load from assets JSON. For now, embed mock data.
    private val recipes: List<Recipe> = listOf(
        Recipe(
            id = "1",
            title = "Lemon Herb Chicken",
            imageUrl = null,
            servings = 4,
            timeMinutes = 35,
            ingredients = listOf("chicken", "lemon", "garlic", "olive oil", "herbs", "salt", "pepper"),
            instructions = listOf(
                "Marinate chicken with lemon, garlic, herbs, oil, salt and pepper.",
                "Sear in pan then finish in oven until cooked through.",
                "Rest 5 minutes and serve."
            )
        ),
        Recipe(
            id = "2",
            title = "Tomato Basil Pasta",
            imageUrl = null,
            servings = 2,
            timeMinutes = 20,
            ingredients = listOf("pasta", "tomato", "basil", "garlic", "olive oil", "salt"),
            instructions = listOf(
                "Boil pasta until al dente.",
                "Sauté garlic in oil, add tomatoes and simmer.",
                "Toss with pasta and basil; season to taste."
            )
        ),
        Recipe(
            id = "3",
            title = "Avocado Toast Deluxe",
            imageUrl = null,
            servings = 1,
            timeMinutes = 10,
            ingredients = listOf("bread", "avocado", "lemon", "chili flakes", "salt", "pepper"),
            instructions = listOf(
                "Toast bread.",
                "Mash avocado with lemon, salt, and pepper.",
                "Spread on toast and garnish with chili flakes."
            )
        )
    )

    // PUBLIC_INTERFACE
    suspend fun getAllRecipes(): List<Recipe> {
        delay(100) // simulate load
        return recipes
    }

    // PUBLIC_INTERFACE
    suspend fun getRecipeById(id: String): Recipe? {
        delay(50)
        return recipes.find { it.id == id }
    }

    // PUBLIC_INTERFACE
    suspend fun searchByIngredients(query: String): List<Recipe> {
        val terms = query.lowercase()
            .split(",", " ")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
        if (terms.isEmpty()) return recipes
        return recipes.filter { r ->
            terms.all { term -> r.ingredients.any { it.contains(term, ignoreCase = true) } }
        }
    }
}
