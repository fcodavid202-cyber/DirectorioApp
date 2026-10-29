package com.example.directorioapp.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.directorioapp.data.model.User
import com.example.directorioapp.data.repository.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UserRepository(application)

    val favorites: StateFlow<List<User>> = repository.favoritesFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), repository.getFavorites())

    fun removeFavorite(userId: Int) {
        viewModelScope.launch {
            repository.removeFavorite(userId)
        }
    }
}
