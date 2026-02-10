package com.lumina.feature.apphiding.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.lumina.feature.apphiding.AppHidingViewModel

@Composable
fun AppHidingScreen(
    viewModel: AppHidingViewModel = hiltViewModel()
) {
    val dataFlow by viewModel.hiddenApps.collectAsState(initial = emptySet())
    Column() {
        Text(
            text = if (dataFlow.isEmpty()) "No hidden apps" else "Test text"

        )
    }
}
