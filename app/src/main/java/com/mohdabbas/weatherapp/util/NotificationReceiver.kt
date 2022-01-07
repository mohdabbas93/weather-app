package com.mohdabbas.weatherapp.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mohdabbas.weatherapp.WeatherApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Mohammad Abbas
 * On: 1/3/22.
 */
class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        CoroutineScope(Dispatchers.IO).launch {
            val temperature = WeatherApplication.WeatherRepository.getRemoteTodayTemperature()
            if (temperature != null)
                NotificationHelper(context).createNotification(temperature.toInt())
        }
    }
}