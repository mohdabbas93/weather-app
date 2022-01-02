package com.mohdabbas.weatherapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohdabbas.weatherapp.data.source.WeatherRepository
import com.mohdabbas.weatherapp.data.source.remote.dto.CityWeatherDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Mohammad Abbas
 * On: 1/2/22.
 */
class HomeViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _weatherData = MutableLiveData<CityWeatherDto>()
    val weatherData: LiveData<CityWeatherDto> = _weatherData

    fun getWeatherData(lat: Double, lng: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = weatherRepository.getWeatherData(lat, lng)
            _weatherData.postValue(response)
        }
    }
}