package org.example.app.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.app.data.repository.RecipeRepository
import org.example.app.domain.model.Recipe

/**
 * ViewModel for Search screen to filter recipes by ingredients.
 */
class SearchViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = RecipeRepository(app)

    private val _query = MutableLiveData("")
    private val _results = MutableLiveData<List<Recipe>>(emptyList())

    val query: LiveData<String> = _query
    val results: LiveData<List<Recipe>> = _results

    fun updateQuery(q: String) {
        _query.value = q
        search()
    }

    private fun search() {
        viewModelScope.launch {
            _results.value = repo.searchByIngredients(_query.value.orEmpty())
        }
    }
}
