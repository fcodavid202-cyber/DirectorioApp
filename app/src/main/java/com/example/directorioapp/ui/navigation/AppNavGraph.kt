package com.example.directorioapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.directorioapp.ui.detail.UserDetailScreen
import com.example.directorioapp.ui.detail.UserDetailViewModel
import com.example.directorioapp.ui.favorites.FavoritesScreen
import com.example.directorioapp.ui.favorites.FavoritesViewModel
import com.example.directorioapp.ui.users.UsersScreen
import com.example.directorioapp.ui.users.UsersViewModel

sealed class Screen(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Users : Screen("users", "Usuarios", Icons.Default.Person)
    object Favorites : Screen("favorites", "Favoritos", Icons.Default.Favorite)
    object Detail : Screen("detail/{userId}", "Detalle del usuario", Icons.Default.Person) {
        fun createRoute(userId: Int) = "detail/$userId"
    }
}

@Composable
fun AppNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val items = listOf(Screen.Users, Screen.Favorites)

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            if (currentRoute != screen.route) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Users.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Users.route) {
                val usersViewModel: UsersViewModel = viewModel()
                UsersScreen(
                    viewModel = usersViewModel,
                    onUserClick = { userId ->
                        navController.navigate(Screen.Detail.createRoute(userId))
                    }
                )
            }
            composable(Screen.Favorites.route) {
                val favoritesViewModel: FavoritesViewModel = viewModel()
                FavoritesScreen(
                    viewModel = favoritesViewModel,
                    onUserClick = { userId ->
                        navController.navigate(Screen.Detail.createRoute(userId))
                    }
                )
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("userId") { type = NavType.IntType })
            ) { backStackEntry ->
                val userId = backStackEntry.arguments?.getInt("userId") ?: 0
                val context = androidx.compose.ui.platform.LocalContext.current
                val detailViewModel: UserDetailViewModel = viewModel(
                    factory = object : androidx.lifecycle.ViewModelProvider.Factory {
                        @Suppress("UNCHECKED_CAST")
                        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                            return UserDetailViewModel(context.applicationContext as android.app.Application, userId) as T
                        }
                    }
                )
                UserDetailScreen(
                    viewModel = detailViewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
