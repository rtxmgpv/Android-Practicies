package com.example.android_practices.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle // <-- ДОБАВЛЕННЫЙ ИМПОРТ
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.android_practices.ui.detail.DetailScreen
import com.example.android_practices.ui.factory.AppViewModelFactory
import com.example.android_practices.ui.favorites.FavoritesScreen
import com.example.android_practices.ui.list.ListScreen
import com.example.android_practices.ui.list.ListViewModel
import com.example.android_practices.ui.settings.SettingsScreen

@Composable
fun AppNavigation(factory: AppViewModelFactory) {
    val navController = rememberNavController()

    val listViewModel: ListViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = factory)

    // Теперь импорт на месте, и эта строка скомпилируется
    val hasActiveFilters by listViewModel.hasActiveFilters.collectAsStateWithLifecycle()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                NavigationBarItem(
                    selected = currentRoute == "list",
                    onClick = { navController.navigate("list") { popUpTo(navController.graph.findStartDestination().id) { saveState = true }; launchSingleTop = true; restoreState = true } },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Список") },
                    label = { Text("Список") }
                )

                NavigationBarItem(
                    selected = currentRoute == "favorites",
                    onClick = { navController.navigate("favorites") { popUpTo(navController.graph.findStartDestination().id) { saveState = true }; launchSingleTop = true; restoreState = true } },
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Избранное") },
                    label = { Text("Избранное") }
                )

                NavigationBarItem(
                    selected = currentRoute == "settings",
                    onClick = { navController.navigate("settings") { popUpTo(navController.graph.findStartDestination().id) { saveState = true }; launchSingleTop = true; restoreState = true } },
                    icon = {
                        BadgedBox(badge = {
                            if (hasActiveFilters) {
                                Badge()
                            }
                        }) {
                            Icon(Icons.Default.Settings, contentDescription = "Настройки")
                        }
                    },
                    label = { Text("Настройки") }
                )
            }
        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = "list", modifier = Modifier.padding(innerPadding)) {
            composable("list") { ListScreen(navController, factory) }
            composable("favorites") { FavoritesScreen(navController = navController, factory = factory) }
            composable("settings") { SettingsScreen(navController, factory) }
            composable(
                route = "detail/{postId}",
                arguments = listOf(navArgument("postId") { type = NavType.IntType })
            ) { backStackEntry ->
                val postId = backStackEntry.arguments?.getInt("postId") ?: 0
                DetailScreen(postId = postId, navController = navController, factory = factory)
            }
        }
    }
}