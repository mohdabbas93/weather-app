package com.mohdabbas.weatherapp.persistence

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by Mohammad Abbas
 * On: 1/2/22.
 */
class PersistenceManager @Inject constructor(@ApplicationContext context: Context) {

    private var sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private val editor = sharedPreferences.edit()

    var isCelsius: Boolean
        get() = sharedPreferences.getBoolean(IS_CELSIUS, true)
        set(value) = editor.putBoolean(IS_CELSIUS, value).apply()

    companion object {
        private const val PREF_NAME = "WeatherSharedPref"
        private const val IS_CELSIUS = "IS_CELSIUS"
    }
}