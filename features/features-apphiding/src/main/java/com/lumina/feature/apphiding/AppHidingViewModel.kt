package com.lumina.feature.apphiding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumina.domain.apps.HiddenAppsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppHidingViewModel @Inject constructor(
    private val hiddenAppsRepository: HiddenAppsRepository
): ViewModel() {
    val hiddenApps = hiddenAppsRepository.allHiddenApps()

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
}
