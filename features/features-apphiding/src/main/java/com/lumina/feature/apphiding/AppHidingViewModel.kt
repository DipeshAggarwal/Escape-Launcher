package com.lumina.feature.apphiding

import android.content.Context
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumina.core.common.FlowDefaults.WhileSubscribedTimeoutMillis
import com.lumina.domain.apps.AppInfo
import com.lumina.domain.apps.HiddenAppsRepository
import com.lumina.domain.apps.InstalledAppsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class AppHidingViewModel @Inject constructor(
    private val hiddenAppsRepository: HiddenAppsRepository,
    private val installedAppsRepository: InstalledAppsRepository
): ViewModel() {
    val installedApps: StateFlow<List<AppInfo>> = flow {
        emit(installedAppsRepository.getInstalledApps())
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(WhileSubscribedTimeoutMillis),
        emptyList()
    )
    val hiddenApps: StateFlow<List<AppInfo>> = hiddenAppsRepository.allHiddenApps()
        .flatMapLatest { packageNames ->
            flow {
                val validApps = mutableListOf<AppInfo>()

                for (pkg in packageNames) {
                    try {
                        val name = installedAppsRepository.getDisplayName(pkg)
                        validApps += AppInfo(pkg, name)
                    } catch (e: PackageManager.NameNotFoundException) {
                        hiddenAppsRepository.unhideApp(pkg)
                    }
                }
                emit(validApps.sortedBy { it.displayName.lowercase() })
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(WhileSubscribedTimeoutMillis),
            emptyList()
        )

    val hiddenPackagesSet: StateFlow<Set<String>> = hiddenAppsRepository.allHiddenApps()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(WhileSubscribedTimeoutMillis),
            emptySet()
        )

    // Temporary
    fun launchApp(context: Context, packageName: String) {
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent?.let { context.startActivity(it) }
    }

    fun hideApp(packageName: String) {
        viewModelScope.launch {
            hiddenAppsRepository.hideApp(packageName)
        }
    }

    fun unhideApp(packageName: String) {
        viewModelScope.launch {
            hiddenAppsRepository.unhideApp(packageName)
        }
    }

    fun toggleHidden(packageName: String) {
        viewModelScope.launch {
            val currentlySelected = hiddenPackagesSet.value.contains(packageName)

            if (currentlySelected) {
                hiddenAppsRepository.unhideApp(packageName)
            } else {
                hiddenAppsRepository.hideApp(packageName)
            }
        }
    }
}
