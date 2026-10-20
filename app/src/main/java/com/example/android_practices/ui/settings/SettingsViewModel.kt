package com.example.android_practices.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_practices.data.local.FilterPreferences
import com.example.android_practices.domain.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(private val repository: PostRepository) : ViewModel() {

    private val _userId = MutableStateFlow(FilterPreferences.DEFAULT_USER_ID)
    val userId: StateFlow<Int> = _userId.asStateFlow()

    private val _limit = MutableStateFlow(FilterPreferences.DEFAULT_LIMIT)
    val limit: StateFlow<Int> = _limit.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getFilterUserId().collect { _userId.value = it }
        }
        viewModelScope.launch {
            repository.getFilterLimit().collect { _limit.value = it }
        }
    }

    fun onUserIdChanged(newId: Int) { _userId.value = newId }
    fun onLimitChanged(newLimit: Int) { _limit.value = newLimit }

    fun save(userId: Int, limit: Int) {
        viewModelScope.launch {
            repository.saveFilters(userId, limit)
        }
    }

    fun reset() {
        val defaultId = FilterPreferences.DEFAULT_USER_ID
        val defaultLimit = FilterPreferences.DEFAULT_LIMIT

        _userId.value = defaultId
        _limit.value = defaultLimit

        viewModelScope.launch {
            repository.saveFilters(defaultId, defaultLimit)
        }
    }
}