package com.lumina.data.apps

import com.lumina.domain.apps.HiddenAppsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreHiddenAppsRepository @Inject constructor(): HiddenAppsRepository {
    override suspend fun hideApp(packageName: String) {
        TODO("Not yet implemented")
    }

    override suspend fun unhideApp(packageName: String) {
        TODO("Not yet implemented")
    }

    override suspend fun isAppHidden(packageName: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun allHiddenApps(): Flow<Set<String>> {
        TODO("Not yet implemented")
    }

}