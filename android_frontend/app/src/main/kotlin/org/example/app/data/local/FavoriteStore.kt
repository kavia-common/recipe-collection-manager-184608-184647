package org.example.app.data.local

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * PUBLIC_INTERFACE
 * FavoriteStore is a lightweight in-memory persistence with SharedPreferences
 * backing for simple favorites storage without Room/annotation processing.
 */
class FavoriteStore private constructor(context: Context) {

    private val prefs = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
    private val live = MutableLiveData<List<FavoriteRecipe>>(emptyList())

    init {
        live.value = readFromPrefs()
    }

    private fun readFromPrefs(): List<FavoriteRecipe> {
        val set = prefs.getStringSet(KEY_IDS, emptySet()) ?: emptySet()
        val list = mutableListOf<FavoriteRecipe>()
        set.forEach { id ->
            val title = prefs.getString("$KEY_TITLE_PREFIX$id", "") ?: ""
            val image = prefs.getString("$KEY_IMAGE_PREFIX$id", null)
            list.add(FavoriteRecipe(id, title, image))
        }
        return list
    }

    private fun writeToPrefs(list: List<FavoriteRecipe>) {
        val editor = prefs.edit()
        val ids = list.map { it.id }.toSet()
        editor.putStringSet(KEY_IDS, ids)
        // clear old title/image keys for removed ids
        val all = prefs.all.keys.toList()
        all.filter { it.startsWith(KEY_TITLE_PREFIX) || it.startsWith(KEY_IMAGE_PREFIX) }
            .forEach { editor.remove(it) }
        list.forEach {
            editor.putString("$KEY_TITLE_PREFIX${it.id}", it.title)
            editor.putString("$KEY_IMAGE_PREFIX${it.id}", it.imageUrl)
        }
        editor.apply()
    }

    // PUBLIC_INTERFACE
    fun getAll(): LiveData<List<FavoriteRecipe>> = live

    // PUBLIC_INTERFACE
    fun isFavorite(id: String): Boolean = live.value?.any { it.id == id } == true

    // PUBLIC_INTERFACE
    fun add(fav: FavoriteRecipe) {
        val current = live.value?.toMutableList() ?: mutableListOf()
        if (current.none { it.id == fav.id }) {
            current.add(fav)
            live.postValue(current)
            writeToPrefs(current)
        }
    }

    // PUBLIC_INTERFACE
    fun removeById(id: String) {
        val current = live.value?.toMutableList() ?: mutableListOf()
        val newList = current.filterNot { it.id == id }
        if (newList.size != current.size) {
            live.postValue(newList)
            writeToPrefs(newList)
        }
    }

    companion object {
        private const val KEY_IDS = "ids"
        private const val KEY_TITLE_PREFIX = "title_"
        private const val KEY_IMAGE_PREFIX = "image_"

        @Volatile private var INSTANCE: FavoriteStore? = null

        // PUBLIC_INTERFACE
        fun getInstance(context: Context): FavoriteStore {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavoriteStore(context.applicationContext).also { INSTANCE = it }
            }
        }
    }
}
