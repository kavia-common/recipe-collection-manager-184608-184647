package org.example.app.ui.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.app.data.local.FavoriteRecipe
import org.example.app.data.local.FavoriteStore
import org.example.app.data.repository.RecipeRepository
import org.example.app.domain.model.Recipe

/**
 * PUBLIC_INTERFACE
 * ViewModel for the Details screen handling current recipe and favorite toggle.
 */
class DetailsViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = RecipeRepository(app)
    private val store = FavoriteStore.getInstance(app)

    private val _recipe = MutableLiveData<Recipe?>()
    val recipe: LiveData<Recipe?> = _recipe

    private val _isFavorite = MutableLiveData(false)
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun load(recipeId: String) {
        viewModelScope.launch {
            val r = repo.getRecipeById(recipeId)
            _recipe.value = r
            _isFavorite.value = if (r != null) store.isFavorite(r.id) else false
        }
    }

    fun toggleFavorite() {
        val r = _recipe.value ?: return
        if (store.isFavorite(r.id)) {
            store.removeById(r.id)
            _isFavorite.postValue(false)
        } else {
            store.add(FavoriteRecipe(r.id, r.title, r.imageUrl))
            _isFavorite.postValue(true)
        }
    }
}
