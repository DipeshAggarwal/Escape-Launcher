package com.lumina.data.apps.di

import com.lumina.data.apps.DataStoreHiddenAppsRepository
import com.lumina.domain.apps.HiddenAppsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppsDataModule {
    @Binds
    @Singleton
    abstract fun bindsHiddenDataStore(hiddenAppsRepository: DataStoreHiddenAppsRepository): HiddenAppsRepository
}
