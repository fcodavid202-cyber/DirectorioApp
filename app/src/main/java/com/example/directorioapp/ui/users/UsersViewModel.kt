package com.example.directorioapp.ui.users

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.directorioapp.data.model.User
import com.example.directorioapp.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface UsersUiState {
    object Loading : UsersUiState
    data class Success(val users: List<User>, val isOffline: Boolean = false) : UsersUiState
    data class Error(val message: String, val canShowOfflineFavorites: Boolean = false) : UsersUiState
    object Empty : UsersUiState
}

class UsersViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UserRepository(application)

    private val _uiState = MutableStateFlow<UsersUiState>(UsersUiState.Loading)
    val uiState: StateFlow<UsersUiState> = _uiState.asStateFlow()

    val favorites: StateFlow<List<User>> = repository.favoritesFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            _uiState.value = UsersUiState.Loading
            try {
                val users = repository.getUsers()
                if (users.isEmpty()) {
                    _uiState.value = UsersUiState.Empty
                } else {
                    _uiState.value = UsersUiState.Success(users, isOffline = false)
                }
            } catch (e: Exception) {
                val localFavorites = repository.getFavorites()
                if (localFavorites.isNotEmpty()) {
                    _uiState.value = UsersUiState.Success(localFavorites, isOffline = true)
                } else {
                    _uiState.value = UsersUiState.Error(
                        message = "No hay conexión a Internet. Comprueba tu conexión e inténtalo de nuevo.",
                        canShowOfflineFavorites = false
                    )
                }
            }
        }
    }

    fun toggleFavorite(user: User) {
        viewModelScope.launch {
            if (repository.isFavorite(user.id)) {
                repository.removeFavorite(user.id)
            } else {
                repository.addFavorite(user)
            }
        }
    }

    fun isFavorite(userId: Int): Boolean {
        return repository.isFavorite(userId)
    }
}
