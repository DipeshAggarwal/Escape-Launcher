package com.geecee.escapelauncher

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

import com.geecee.escapelauncher.utils.AnalyticsProxyImpl
import com.geecee.escapelauncher.utils.MessagingInitializerImpl
import com.geecee.escapelauncher.utils.WeatherImpl
import com.geecee.escapelauncher.utils.analyticsProxy
import com.geecee.escapelauncher.utils.managers.Migration
import com.geecee.escapelauncher.utils.messagingInitializer
import com.geecee.escapelauncher.utils.weatherProxy
import com.lumina.core.logging.Logger
import javax.inject.Inject

@HiltAndroidApp
class LauncherApplication: Application() {
    @Inject lateinit var logger: Logger

    override fun onCreate() {
        super.onCreate()
        logger.d("INIT", "Launcher started and DI is working.")

        Migration(this).migrateToUnifiedPrefs()
        // Initialize flavor-specific proxies
        analyticsProxy = AnalyticsProxyImpl()
        messagingInitializer = MessagingInitializerImpl()
        weatherProxy = WeatherImpl()
    }
}
