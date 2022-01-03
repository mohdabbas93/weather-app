package com.mohdabbas.weatherapp.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by Mohammad Abbas
 * On: 1/3/22.
 */
class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        NotificationHelper(context).createNotification()
    }
}