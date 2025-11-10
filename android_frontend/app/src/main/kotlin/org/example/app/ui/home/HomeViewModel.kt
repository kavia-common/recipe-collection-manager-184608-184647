package org.example.app.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.app.data.repository.RecipeRepository
import org.example.app.domain.model.Recipe

/**
 * ViewModel for Home screen to load all recipes.
 */
class HomeViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = RecipeRepository(app)

    private val _recipes = MutableLiveData<List<Recipe>>(emptyList())
    val recipes: LiveData<List<Recipe>> = _recipes

    fun load() {
        viewModelScope.launch {
            _recipes.value = repo.getAllRecipes()
        }
    }
}
