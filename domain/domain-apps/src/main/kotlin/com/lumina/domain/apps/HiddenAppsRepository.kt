package com.lumina.domain.apps

import kotlinx.coroutines.flow.Flow

interface HiddenAppsRepository {
    suspend fun addHiddenApp(packageName: String)
    suspend fun removeHiddenApp(packageName: String)
    suspend fun setHiddenApps(packageNames: List<String>)
    fun allHiddenApps(): Flow<Set<String>>
}
