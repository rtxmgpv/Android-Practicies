package com.example.android_practices.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_practices.domain.model.Post
import com.example.android_practices.domain.repository.PostRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: PostRepository
) : ViewModel() {

    val favorites: StateFlow<List<Post>> = repository.getFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun removeFavorite(postId: Int) {
        viewModelScope.launch {
            repository.removeFavorite(postId)
        }
    }
}