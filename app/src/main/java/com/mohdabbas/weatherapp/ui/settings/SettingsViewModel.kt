package com.mohdabbas.weatherapp.ui.settings

import androidx.lifecycle.ViewModel
import com.mohdabbas.weatherapp.persistence.PersistenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Mohammad Abbas
 * On: 1/7/22.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val persistenceManager: PersistenceManager
) : ViewModel() {

    fun isCelsius() = persistenceManager.isCelsius
    fun setIsCelsius(isCelsius: Boolean) {
        persistenceManager.isCelsius = isCelsius
    }
}