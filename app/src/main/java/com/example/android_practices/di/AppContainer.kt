package com.example.android_practices.di

import android.content.Context
import androidx.room.Room
import com.example.android_practices.data.local.AppDatabase
import com.example.android_practices.data.local.FilterPreferences
import com.example.android_practices.data.remote.JsonPlaceholderApi
import com.example.android_practices.data.remote.NetworkModule
import com.example.android_practices.data.repository.PostRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FilterCache {
    val hasActiveFilters = MutableStateFlow(false)
}

class AppContainer(context: Context) {
    private val database: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "app_database"
    ).build()

    private val api: JsonPlaceholderApi = NetworkModule.api
    private val filterPreferences = FilterPreferences(context)

    val filterCache = FilterCache()

    val repository = PostRepositoryImpl(
        api = api,
        favoriteDao = database.favoriteDao(),
        filterPreferences = filterPreferences,
        filterCache = filterCache
    )

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val savedId = filterPreferences.userIdFlow.first()
            val savedLimit = filterPreferences.limitFlow.first()
            val isDefault = savedId == FilterPreferences.DEFAULT_USER_ID && savedLimit == FilterPreferences.DEFAULT_LIMIT
            filterCache.hasActiveFilters.value = !isDefault
        }
    }
}