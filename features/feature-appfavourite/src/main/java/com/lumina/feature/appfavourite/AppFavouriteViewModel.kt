package com.lumina.feature.appfavourite

import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumina.core.common.FlowDefaults.WhileSubscribedTimeoutMillis
import com.lumina.domain.apps.AppInfo
import com.lumina.domain.apps.FavouriteAppsRepository
import com.lumina.domain.apps.InstalledAppsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

import javax.inject.Inject


@HiltViewModel
class AppFavouriteViewModel @Inject constructor(
    private val favouriteAppsRepository: FavouriteAppsRepository,
    private val installedAppsRepository: InstalledAppsRepository
): ViewModel() {
    init {
        viewModelScope.launch {
            favouriteAppsRepository.allFavouriteApps()
                .distinctUntilChanged()
                .collect { packageNames ->
                val installedPackages = installedAppsRepository
                    .getInstalledApps()
                    .map { it.packageName }
                    .toSet()

                val uninstalledPackages = packageNames.filterNot { it in installedPackages }
                if (uninstalledPackages.isNotEmpty()) {
                    favouriteAppsRepository.removeFavouriteApps(uninstalledPackages)
                }
            }
        }
    }
    val installedApps: StateFlow<List<AppInfo>> = flow {
        emit(installedAppsRepository.getInstalledApps())
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(WhileSubscribedTimeoutMillis),
        emptyList()
    )

    val favouriteApps: StateFlow<List<AppInfo>> = favouriteAppsRepository.allFavouriteApps()
        .map { packageNames ->
            packageNames.mapNotNull { pkg ->
                try {
                    val name = installedAppsRepository.getDisplayName(pkg)
                    AppInfo(pkg, name)
                } catch (e: PackageManager.NameNotFoundException) {
                    null
                }
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(WhileSubscribedTimeoutMillis),
            emptyList()
        )

    fun addFavouriteApp(packageName: String) {
        viewModelScope.launch {
            favouriteAppsRepository.addFavouriteApp(packageName)
        }
    }

    fun removeFavouriteApp(packageName: String) {
        viewModelScope.launch {
            favouriteAppsRepository.removeFavouriteApp(packageName)
        }
    }

    fun toggleFavouriteApp(packageName: String) {
        viewModelScope.launch {
            val currentlySelected = favouriteApps.value.any { it.packageName == packageName }

            if (currentlySelected) {
                favouriteAppsRepository.removeFavouriteApp(packageName)
            } else {
                favouriteAppsRepository.addFavouriteApp(packageName)
            }
        }
    }
}
