package com.example.directorioapp.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.directorioapp.data.model.Post
import com.example.directorioapp.data.model.User
import com.example.directorioapp.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface PostsUiState {
    object Loading : PostsUiState
    data class Success(val posts: List<Post>) : PostsUiState
    data class Error(val message: String) : PostsUiState
    object Empty : PostsUiState
}

class UserDetailViewModel(application: Application, val userId: Int) : AndroidViewModel(application) {
    private val repository = UserRepository(application)

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _postsState = MutableStateFlow<PostsUiState>(PostsUiState.Loading)
    val postsState: StateFlow<PostsUiState> = _postsState.asStateFlow()

    val isFavorite: StateFlow<List<User>> = repository.favoritesFlow

    init {
        loadDetail()
    }

    private fun loadDetail() {
        viewModelScope.launch {
            try {
                val cachedUser = repository.getFavorites().find { it.id == userId }
                if (cachedUser != null) {
                    _user.value = cachedUser
                } else {
                    val allUsers = repository.getUsers()
                    _user.value = allUsers.find { it.id == userId }
                }
            } catch (e: Exception) {
                val cachedUser = repository.getFavorites().find { it.id == userId }
                _user.value = cachedUser
            }

            _postsState.value = PostsUiState.Loading
            try {
                val posts = repository.getUserPosts(userId)
                if (posts.isEmpty()) {
                    _postsState.value = PostsUiState.Empty
                } else {
                    _postsState.value = PostsUiState.Success(posts)
                }
            } catch (e: Exception) {
                _postsState.value = PostsUiState.Error("No hay conexión a Internet. Comprueba tu conexión e inténtalo de nuevo.")
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

    fun isUserFavorite(userId: Int): Boolean {
        return repository.isFavorite(userId)
    }
}
