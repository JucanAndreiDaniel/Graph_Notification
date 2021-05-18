package com.scfnotification.notifyme.data

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RoomApplication : Application() {
    val CHANNEL_ID = "ALARM_SERVICE_CHANNEL"

    override fun onCreate() {
        super.onCreate()
        createNotificationChannnel()
    }

    private fun createNotificationChannnel() {
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "Alarm Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(
            NotificationManager::class.java
        )
        manager.createNotificationChannel(serviceChannel)
    }
}
