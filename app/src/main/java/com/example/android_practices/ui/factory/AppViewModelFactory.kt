package com.example.android_practices.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_practices.di.AppContainer
import com.example.android_practices.domain.usecase.GetPostDetailsUseCase
import com.example.android_practices.domain.usecase.GetPostsUseCase
import com.example.android_practices.ui.detail.DetailViewModel
import com.example.android_practices.ui.favorites.FavoritesViewModel
import com.example.android_practices.ui.list.ListViewModel
import com.example.android_practices.ui.settings.SettingsViewModel

class AppViewModelFactory(private val container: AppContainer) : ViewModelProvider.Factory {

    private val getPostsUseCase = GetPostsUseCase(container.repository)
    private val getPostDetailsUseCase = GetPostDetailsUseCase(container.repository)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            // Передаем container.filterCache третьим аргументом!
            modelClass.isAssignableFrom(ListViewModel::class.java) ->
                ListViewModel(getPostsUseCase, container.repository, container.filterCache) as T

            modelClass.isAssignableFrom(DetailViewModel::class.java) ->
                DetailViewModel(getPostDetailsUseCase, container.repository) as T

            modelClass.isAssignableFrom(SettingsViewModel::class.java) ->
                SettingsViewModel(container.repository) as T

            modelClass.isAssignableFrom(FavoritesViewModel::class.java) ->
                FavoritesViewModel(container.repository) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}