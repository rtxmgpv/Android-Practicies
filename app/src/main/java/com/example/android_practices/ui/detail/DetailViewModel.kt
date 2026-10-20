package com.example.android_practices.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_practices.domain.model.Comment
import com.example.android_practices.domain.model.Post
import com.example.android_practices.domain.repository.PostRepository
import com.example.android_practices.domain.usecase.GetPostDetailsUseCase
import com.example.android_practices.ui.common.UiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class DetailData(val post: Post, val comments: List<Comment>)

class DetailViewModel(
    private val getPostDetailsUseCase: GetPostDetailsUseCase,
    private val repository: PostRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<DetailData>>(UiState.Loading)
    val uiState: StateFlow<UiState<DetailData>> = _uiState.asStateFlow()

    fun loadDetails(postId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val (post, comments) = getPostDetailsUseCase(postId)
                _uiState.value = UiState.Success(DetailData(post, comments))
            } catch (e: Exception) {
                val favoritePosts = repository.getFavorites().first()
                val localPost = favoritePosts.find { it.id == postId }

                if (localPost != null) {
                    val offlineComment = Comment(
                        id = -1,
                        name = "Нет сети",
                        email = "",
                        body = "Комментарии недоступны в офлайн-режиме."
                    )
                    _uiState.value = UiState.Success(DetailData(localPost, listOf(offlineComment)))
                } else {
                    _uiState.value = UiState.Error(e.message ?: "Ошибка загрузки деталей")
                }
            }
        }
    }

    fun observeIsFavorite(postId: Int): Flow<Boolean> = repository.isFavorite(postId)

    fun toggleFavorite(post: Post, isCurrentlyFavorite: Boolean) {
        viewModelScope.launch {
            if (isCurrentlyFavorite) repository.removeFavorite(post.id)
            else repository.addFavorite(post)
        }
    }
}