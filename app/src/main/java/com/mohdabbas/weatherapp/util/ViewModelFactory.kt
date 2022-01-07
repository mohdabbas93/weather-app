package com.mohdabbas.weatherapp.util

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mohdabbas.weatherapp.WeatherApplication
import com.mohdabbas.weatherapp.ui.search.CitySearchViewModel

/**
 * Created by Mohammad Abbas
 * On: 1/7/22.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(CitySearchViewModel::class.java) ->
                    CitySearchViewModel(WeatherApplication.citySearchRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance() =
            INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: ViewModelFactory().also { INSTANCE = it }
            }
    }
}