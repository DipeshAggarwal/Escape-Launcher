package com.lumina.domain.apps

import kotlinx.coroutines.flow.Flow

interface HiddenAppsRepository {
    suspend fun hideApp(packageName: String)
    suspend fun unhideApp(packageName: String)
    suspend fun isAppHidden(packageName: String): Boolean
    fun allHiddenApps(): Flow<Set<String>>
}
