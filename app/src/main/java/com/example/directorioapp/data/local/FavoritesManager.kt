package com.example.directorioapp.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.directorioapp.data.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavoritesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val favoritesKey = "favorite_users_list"

    private val _favoritesFlow = MutableStateFlow<List<User>>(loadFavorites())
    val favoritesFlow: StateFlow<List<User>> = _favoritesFlow.asStateFlow()

    fun getFavorites(): List<User> {
        return loadFavorites()
    }

    fun isFavorite(userId: Int): Boolean {
        return loadFavorites().any { it.id == userId }
    }

    fun addFavorite(user: User) {
        val current = loadFavorites().toMutableList()
        if (current.none { it.id == user.id }) {
            current.add(user)
            saveFavorites(current)
        }
    }

    fun removeFavorite(userId: Int) {
        val current = loadFavorites().toMutableList()
        val removed = current.removeAll { it.id == userId }
        if (removed) {
            saveFavorites(current)
        }
    }

    private fun loadFavorites(): List<User> {
        val json = prefs.getString(favoritesKey, null) ?: return emptyList()
        return try {
            val type = object : TypeToken<List<User>>() {}.type
            gson.fromJson(json, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun saveFavorites(list: List<User>) {
        val json = gson.toJson(list)
        prefs.edit().putString(favoritesKey, json).apply()
        _favoritesFlow.value = list
    }
}
