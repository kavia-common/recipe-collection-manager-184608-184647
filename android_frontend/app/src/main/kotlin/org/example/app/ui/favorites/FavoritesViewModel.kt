package org.example.app.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import org.example.app.data.local.FavoriteRecipe
import org.example.app.data.local.FavoriteStore

/**
 * PUBLIC_INTERFACE
 * ViewModel for Favorites screen backed by FavoriteStore.
 */
class FavoritesViewModel(app: Application) : AndroidViewModel(app) {
    private val store = FavoriteStore.getInstance(app)

    val favorites: LiveData<List<FavoriteRecipe>> = store.getAll()

    fun remove(id: String) {
        store.removeById(id)
    }
}
