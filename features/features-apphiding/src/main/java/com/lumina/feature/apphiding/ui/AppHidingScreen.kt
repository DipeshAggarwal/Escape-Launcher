package com.lumina.feature.apphiding.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.lumina.feature.apphiding.AppHidingViewModel

@Composable
fun AppHidingScreen(
    viewModel: AppHidingViewModel = hiltViewModel()
) {
    val hiddenApps by viewModel.hiddenApps.collectAsState()

    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 0.dp, 20.dp, 0.dp)
    ) {
        if (hiddenApps.isEmpty()) {
            item {
                Text(text = "No Hiddden Apps.")
            }
        } else {
            items(hiddenApps, key = { it.packageName }) { app ->
                TextButton(onClick = { viewModel.unhideApp(app.packageName) }) {
                    Text(text = app.displayName)
                }
            }
        }
    }
}
