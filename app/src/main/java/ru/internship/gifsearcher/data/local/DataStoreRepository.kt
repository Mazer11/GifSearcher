package ru.internship.gifsearcher.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.internship.gifsearcher.data.common.Constants.DATASTORE_NAME
import ru.internship.gifsearcher.data.common.Constants.THEME_PREFERENCE
import java.io.IOException

class DataStoreRepository(
    private val context: Context
) {

    companion object {
        private val Context.dataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }

    private object PreferenceKeys {
        val theme_key = booleanPreferencesKey(THEME_PREFERENCE)
    }

    val getThemeValue: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.d("Get theme value", exception.message.toString())
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val theme = preferences[PreferenceKeys.theme_key] ?: true
            theme
        }

    suspend fun switchThemePreference() {
        context.dataStore.edit { pref ->
            if (pref[PreferenceKeys.theme_key] == null)
                pref[PreferenceKeys.theme_key] = false
            else
                pref[PreferenceKeys.theme_key] = !pref[PreferenceKeys.theme_key]!!
        }
    }

}