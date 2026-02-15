package com.lumina.data.apps.di

import com.lumina.data.apps.DataStoreFavouriteAppsRepository
import com.lumina.domain.apps.FavouriteAppsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FavouriteAppsModule {
    @Binds
    @Singleton
    abstract fun bindsFavouriteAppsModule(
        favouriteAppsRepository: DataStoreFavouriteAppsRepository
    ): FavouriteAppsRepository
}
