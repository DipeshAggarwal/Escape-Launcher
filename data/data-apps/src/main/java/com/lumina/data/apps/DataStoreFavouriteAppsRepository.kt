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

    override suspend fun addFavourite(packageName: String) {
        dataStore.edit { prefs ->
            val currentFavourites = prefs[favouriteAppsKey]?.split(DELIMITER)
                ?.toMutableList()
                ?: mutableListOf()

            if (!currentFavourites.contains(packageName)) {
                val updatedFavourites = currentFavourites + packageName
                prefs[favouriteAppsKey] = updatedFavourites.joinToString(separator=DELIMITER)
            }
        }
    }

    override suspend fun removeFavourite(packageName: String) {
        dataStore.edit { prefs ->
            val currentFavourites = prefs[favouriteAppsKey]?.split(DELIMITER)
                ?.toMutableList()
                ?: return@edit

            if (currentFavourites.contains(packageName)) {
                val updatedFavourites = currentFavourites - packageName
                prefs[favouriteAppsKey] = updatedFavourites.joinToString(separator=DELIMITER)
            }
        }

    }

    override suspend fun reorderFavourites(fromIndex: Int, toIndex: Int) {
        dataStore.edit { prefs ->
            val currentFavourites = prefs[favouriteAppsKey]?.split(DELIMITER)
                ?.toMutableList()
                ?: return@edit

            if (fromIndex in currentFavourites.indices && toIndex in currentFavourites.indices) {
                Collections.swap(currentFavourites, fromIndex, toIndex)
                prefs[favouriteAppsKey] = currentFavourites.joinToString(DELIMITER)
            } else {
                logger.w(TAG,
                    "Invalid indices: from=$fromIndex, to=$toIndex, size=${currentFavourites.size}"
                )
            }
        }
    }

    override fun allFavourites(): Flow<List<String>> {
        return dataStore.data.map { prefs ->
            prefs[favouriteAppsKey]?.split(DELIMITER)?.filter { it.isNotEmpty() }
                ?: emptyList()
        }
    }
}
