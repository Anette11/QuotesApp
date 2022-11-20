package com.example.quotesapp.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class PreferencesDataStoreProvider(
    private val context: Context
) {
    private val preferencesDataStoreName = "preferences_data_store_name"

    private object PreferencesKeys {
        val isChecked = booleanPreferencesKey("isChecked")
    }

    private val Context.datastore by preferencesDataStore(
        name = preferencesDataStoreName
    )

    suspend fun saveIsChecked(
        isChecked: Boolean
    ) = context.datastore.edit { preferences ->
        preferences[PreferencesKeys.isChecked] = isChecked
    }

    suspend fun getIsChecked(): Boolean {
        val preferences = context.datastore.data.first()
        return preferences[PreferencesKeys.isChecked] ?: false
    }
}