package com.example.directorioapp.data.repository

import android.content.Context
import com.example.directorioapp.data.local.FavoritesManager
import com.example.directorioapp.data.model.Post
import com.example.directorioapp.data.model.User
import com.example.directorioapp.data.remote.ApiService
import com.example.directorioapp.data.remote.RetrofitClient
import kotlinx.coroutines.flow.StateFlow

class UserRepository(context: Context) {
    private val apiService: ApiService = RetrofitClient.apiService
    private val favoritesManager: FavoritesManager = FavoritesManager(context)

    val favoritesFlow: StateFlow<List<User>> = favoritesManager.favoritesFlow

    suspend fun getUsers(): List<User> {
        return apiService.getUsers()
    }

    suspend fun getUserPosts(userId: Int): List<Post> {
        return apiService.getUserPosts(userId)
    }

    fun getFavorites(): List<User> {
        return favoritesManager.getFavorites()
    }

    fun isFavorite(userId: Int): Boolean {
        return favoritesManager.isFavorite(userId)
    }

    fun addFavorite(user: User) {
        favoritesManager.addFavorite(user)
    }

    fun removeFavorite(userId: Int) {
        favoritesManager.removeFavorite(userId)
    }
}
