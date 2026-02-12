package com.lumina.data.apps

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.lumina.core.logging.Logger
import com.lumina.domain.apps.AppInfo
import com.lumina.domain.apps.InstalledAppsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PackageManagerInstalledAppsRepository @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val logger: Logger
): InstalledAppsRepository {
    private val pm = context.packageManager
    private val TAG = this::class.java.simpleName

    override suspend fun getDisplayName(packageName: String): String {
        return try {
            val appInfo = pm.getApplicationInfo(packageName, 0)
            pm.getApplicationLabel(appInfo).toString()
        } catch (e: PackageManager.NameNotFoundException) {
            logger.w(TAG, "App not found: $packageName", e)
            throw e
        }
    }

    override suspend fun getInstalledApps(): List<AppInfo> {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        val resolveInfo = pm.queryIntentActivities(intent, 0)

        return resolveInfo.map {
            val packageName = it.activityInfo.packageName
            val label = it.loadLabel(pm).toString()

            AppInfo(packageName, label)
        }
        .filterNot { it.packageName == context.packageName }
        .distinctBy { it.packageName } // Some apps expose multiple launchable activities
        .sortedBy { it.displayName.lowercase() }
    }
}
