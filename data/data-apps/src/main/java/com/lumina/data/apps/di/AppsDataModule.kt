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
abstract class HiddenAppsDataModule {
    @Binds
    @Singleton
    abstract fun bindsHiddenRepository(
        hiddenAppsRepository: DataStoreHiddenAppsRepository
    ): HiddenAppsRepository
}
