package com.example.directorioapp.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.directorioapp.data.model.Post

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    viewModel: UserDetailViewModel,
    onBackClick: () -> Unit
) {
    val user by viewModel.user.collectAsStateWithLifecycle()
    val postsState by viewModel.postsState.collectAsStateWithLifecycle()
    val favorites by viewModel.isFavorite.collectAsStateWithLifecycle()

    val isFav = user?.let { u -> favorites.any { it.id == u.id } } ?: false

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del usuario") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    user?.let { u ->
                        IconButton(onClick = { viewModel.toggleFavorite(u) }) {
                            Icon(
                                imageVector = if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = if (isFav) "Quitar de favoritos" else "Añadir a favoritos",
                                tint = if (isFav) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        if (user == null) {
            Box(modifier = Modifier.padding(innerPadding).fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            val u = user!!
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Información Personal", style = MaterialTheme.typography.titleLarge)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Nombre: ${u.name}")
                            Text(text = "Usuario: ${u.username}")
                            Text(text = "Correo: ${u.email}")
                            Text(text = "Teléfono: ${u.phone ?: "No disponible"}")
                            Text(text = "Sitio Web: ${u.website ?: "No disponible"}")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Empresa: ${u.company?.name ?: "No disponible"}")
                            Text(text = "Dirección: ${u.address?.street}, ${u.address?.suite}, ${u.address?.city} (${u.address?.zipcode})")
                        }
                    }
                }

                item {
                    Text(text = "Publicaciones", style = MaterialTheme.typography.titleLarge)
                }

                when (val pState = postsState) {
                    is PostsUiState.Loading -> {
                        item {
                            Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    is PostsUiState.Success -> {
                        items(pState.posts) { post ->
                            PostCard(post = post)
                        }
                    }
                    is PostsUiState.Error -> {
                        item {
                            Surface(
                                color = MaterialTheme.colorScheme.errorContainer,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = pState.message,
                                    modifier = Modifier.padding(16.dp),
                                    color = MaterialTheme.colorScheme.onErrorContainer
                                )
                            }
                        }
                    }
                    is PostsUiState.Empty -> {
                        item {
                            Text("Este usuario no tiene publicaciones.")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PostCard(post: Post) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = post.displayTitle, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = post.displayBody, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
