package com.lumina.domain.apps

import kotlinx.coroutines.flow.Flow

interface FavouriteAppsRepository {
    suspend fun addFavouriteApp(packageName: String)
    suspend fun removeFavouriteApp(packageName: String)
    suspend fun removeFavouriteApps(packageNames: List<String>)
    suspend fun reorderFavouriteApps(fromIndex: Int, toIndex: Int)

    fun allFavouriteApps(): Flow<List<String>>
}
