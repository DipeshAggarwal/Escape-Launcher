package com.lumina.data.apps

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.lumina.core.logging.Logger
import com.lumina.domain.apps.FavouriteAppsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Collections
import javax.inject.Inject

private const val FAVOURITE_APPS_KEY = "favourite_apps"
private const val DELIMITER = ","

class DataStoreFavouriteAppsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val logger: Logger
): FavouriteAppsRepository {
    private val favouriteAppsKey = stringPreferencesKey(FAVOURITE_APPS_KEY)
    private val TAG = this::class.java.simpleName

    override suspend fun addFavouriteApp(packageName: String) {
        dataStore.edit { prefs ->
            val currentFavourites = prefs[favouriteAppsKey]?.split(DELIMITER)
                ?: emptyList()

            if (!currentFavourites.contains(packageName)) {
                val updatedFavourites = currentFavourites + packageName
                prefs[favouriteAppsKey] = updatedFavourites.joinToString(separator=DELIMITER)
            }
        }
    }

    override suspend fun removeFavouriteApp(packageName: String) {
        dataStore.edit { prefs ->
            val currentFavourites = prefs[favouriteAppsKey]?.split(DELIMITER)
                ?: return@edit

            if (currentFavourites.contains(packageName)) {
                val updatedFavourites = currentFavourites - packageName
                prefs[favouriteAppsKey] = updatedFavourites.joinToString(separator=DELIMITER)
            }
        }

    }

    override suspend fun removeFavouriteApps(packageNames: List<String>) {
        if (packageNames.isEmpty()) return

        dataStore.edit { prefs ->
            val currentFavourites = prefs[favouriteAppsKey]?.split(DELIMITER)
                ?: return@edit

            val updatedFavourites = currentFavourites.filterNot { it in packageNames }
            prefs[favouriteAppsKey] = updatedFavourites.joinToString(separator=DELIMITER)
        }
    }

    override suspend fun reorderFavouriteApps(fromIndex: Int, toIndex: Int) {
        dataStore.edit { prefs ->
            val currentFavourites = prefs[favouriteAppsKey]?.split(DELIMITER)
                ?.toMutableList()
                ?: return@edit

            if (fromIndex in currentFavourites.indices && toIndex in currentFavourites.indices) {
                val selectedApp = currentFavourites.removeAt(fromIndex)
                currentFavourites.add(toIndex, selectedApp)
                prefs[favouriteAppsKey] = currentFavourites.joinToString(DELIMITER)
            } else {
                logger.w(TAG,
                    "Invalid indices: from=$fromIndex, to=$toIndex, size=${currentFavourites.size}"
                )
            }
        }
    }

    override fun allFavouriteApps(): Flow<List<String>> {
        return dataStore.data.map { prefs ->
            prefs[favouriteAppsKey]?.split(DELIMITER)?.filter { it.isNotEmpty() }
                ?: emptyList()
        }
    }
}
