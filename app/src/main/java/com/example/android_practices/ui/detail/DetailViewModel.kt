package com.example.android_practices.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_practices.domain.model.Comment
import com.example.android_practices.domain.model.Post
import com.example.android_practices.domain.usecase.GetPostDetailsUseCase
import com.example.android_practices.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DetailData(
    val post: Post,
    val comments: List<Comment>
)

class DetailViewModel(
    private val getPostDetailsUseCase: GetPostDetailsUseCase
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
                _uiState.value = UiState.Error(e.message ?: "Ошибка загрузки деталей")
            }
        }
    }
}