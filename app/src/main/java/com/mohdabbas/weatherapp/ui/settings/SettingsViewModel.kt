package com.mohdabbas.weatherapp.ui.settings

import androidx.lifecycle.ViewModel
import com.mohdabbas.weatherapp.persistence.PersistenceManager

/**
 * Created by Mohammad Abbas
 * On: 1/7/22.
 */
class SettingsViewModel(private val persistenceManager: PersistenceManager) : ViewModel() {

    fun isCelsius() = persistenceManager.isCelsius
    fun setIsCelsius(isCelsius: Boolean) {
        persistenceManager.isCelsius = isCelsius
    }
}