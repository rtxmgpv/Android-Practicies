package com.example.android_practices.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "filter_settings")

class FilterPreferences(private val context: Context) {
    companion object {
        val USER_ID_KEY = intPreferencesKey("user_id")
        val LIMIT_KEY = intPreferencesKey("limit")

        const val DEFAULT_USER_ID = 0
        const val DEFAULT_LIMIT = 20
    }

    val userIdFlow: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[USER_ID_KEY] ?: DEFAULT_USER_ID
    }

    val limitFlow: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[LIMIT_KEY] ?: DEFAULT_LIMIT
    }

    suspend fun saveFilters(userId: Int, limit: Int) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = userId
            prefs[LIMIT_KEY] = limit
        }
    }
}