package com.example.android_practices.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_practices.data.remote.NetworkModule
import com.example.android_practices.data.repository.PostRepositoryImpl
import com.example.android_practices.domain.usecase.GetPostDetailsUseCase
import com.example.android_practices.domain.usecase.GetPostsUseCase
import com.example.android_practices.ui.detail.DetailViewModel
import com.example.android_practices.ui.list.ListViewModel

class AppViewModelFactory : ViewModelProvider.Factory {

    private val repository by lazy { PostRepositoryImpl(NetworkModule.api) }
    private val getPostsUseCase by lazy { GetPostsUseCase(repository) }
    private val getPostDetailsUseCase by lazy { GetPostDetailsUseCase(repository) }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ListViewModel::class.java) -> {
                ListViewModel(getPostsUseCase) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(getPostDetailsUseCase) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}