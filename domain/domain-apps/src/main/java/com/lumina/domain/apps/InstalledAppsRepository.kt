package com.lumina.domain.apps

interface InstalledAppsRepository {
    suspend fun getDisplayName(packageName: String): String
    suspend fun getInstalledApps(): List<AppInfo>
}
