package com.lumina.domain.apps

import kotlinx.coroutines.flow.Flow

interface FavouriteAppsRepository {
    suspend fun addFavourite(packageName: String)
    suspend fun removeFavourite(packageName: String)
    suspend fun reorderFavourites(fromIndex: Int, toIndex: Int)

    fun allFavourites(): Flow<List<String>>
}
