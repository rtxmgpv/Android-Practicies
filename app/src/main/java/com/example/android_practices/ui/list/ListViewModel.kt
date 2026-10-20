package com.example.android_practices.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_practices.di.FilterCache
import com.example.android_practices.domain.model.Post
import com.example.android_practices.domain.repository.PostRepository
import com.example.android_practices.domain.usecase.GetPostsUseCase
import com.example.android_practices.ui.common.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModel(
    private val getPostsUseCase: GetPostsUseCase,
    private val repository: PostRepository,
    private val filterCache: FilterCache
) : ViewModel() {

    val hasActiveFilters: StateFlow<Boolean> = filterCache.hasActiveFilters.asStateFlow()

    val favoriteIds: StateFlow<Set<Int>> = repository.getFavorites()
        .map { posts -> posts.map { it.id }.toSet() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    // Триггер для кнопки "Повторить"
    private val refreshTrigger = MutableSharedFlow<Unit>(replay = 1).apply { tryEmit(Unit) }

    val uiState: StateFlow<UiState<List<Post>>> = combine(
        repository.getFilterUserId(),
        repository.getFilterLimit(),
        refreshTrigger
    ) { userId, limit, _ -> Pair(userId, limit) }
        .flatMapLatest { (userId, limit) ->
            flow {
                emit(UiState.Loading)
                try {
                    val posts = getPostsUseCase(limit, if (userId == 0) null else userId)
                    emit(UiState.Success(posts))
                } catch (e: Exception) {
                    emit(UiState.Error(e.message ?: "Ошибка сети"))
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)

    fun addFavorite(post: Post) {
        viewModelScope.launch { repository.addFavorite(post) }
    }

    fun removeFavorite(postId: Int) {
        viewModelScope.launch { repository.removeFavorite(postId) }
    }

    fun retry() {
        viewModelScope.launch { refreshTrigger.emit(Unit) }
    }
}