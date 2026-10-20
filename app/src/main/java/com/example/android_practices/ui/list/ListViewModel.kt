package com.example.android_practices.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_practices.domain.model.Post
import com.example.android_practices.domain.usecase.GetPostsUseCase
import com.example.android_practices.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListViewModel(
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Post>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Post>>> = _uiState.asStateFlow()

    init {
        loadPosts()
    }

    fun loadPosts() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val posts = getPostsUseCase(limit = 20)
                _uiState.value = UiState.Success(posts)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Ошибка загрузки данных")
            }
        }
    }
}