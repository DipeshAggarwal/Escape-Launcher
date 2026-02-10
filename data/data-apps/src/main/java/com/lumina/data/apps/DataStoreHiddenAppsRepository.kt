package com.lumina.data.apps

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.lumina.domain.apps.HiddenAppsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreHiddenAppsRepository @Inject constructor(private val dataStore: DataStore<Preferences>): HiddenAppsRepository {
    private val hiddenAppsKey = stringSetPreferencesKey("hidden_apps")

    override suspend fun hideApp(packageName: String) {
        dataStore.edit { prefs ->
            val currentHiddenApps = prefs[hiddenAppsKey] ?: emptySet()
            val updatedHiddenApps = currentHiddenApps + packageName

            prefs[hiddenAppsKey] = updatedHiddenApps
        }
    }

    override suspend fun unhideApp(packageName: String) {
        dataStore.edit { prefs ->
            val currentHiddenApps = prefs[hiddenAppsKey] ?: emptySet()
            val updatedHiddenApps = currentHiddenApps - packageName

            prefs[hiddenAppsKey] = updatedHiddenApps
        }
    }

    override suspend fun isAppHidden(packageName: String): Boolean {
        val currentHiddenApps = dataStore.data.first()[hiddenAppsKey] ?: emptySet()
        return packageName in currentHiddenApps
    }

    override fun allHiddenApps(): Flow<Set<String>> {
        return dataStore.data.map { prefs -> prefs[hiddenAppsKey] ?: emptySet() }
    }

}
