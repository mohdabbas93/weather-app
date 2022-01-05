package com.mohdabbas.weatherapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohdabbas.weatherapp.data.Result
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

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getCurrentLocationWeatherData() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            val response = weatherRepository.getCurrentLocationWeatherData()
            _loading.postValue(false)
            _weatherData.postValue(response)
        }
    }

    fun setLoading() {
        _loading.value = true
    }

    private val _weatherData = MutableLiveData<Result<CityWeatherDto>>()
    val weatherData: LiveData<Result<CityWeatherDto>> = _weatherData

    fun getWeatherData(lat: Double, lng: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            val response = weatherRepository.getWeatherData(lat, lng)
            _loading.postValue(false)
            _weatherData.postValue(response)
        }
    }

    private val _isCityFavorite = MutableLiveData<Boolean>()
    val isCityFavorite: LiveData<Boolean> = _isCityFavorite

    fun isCityFavorite(lat: Double, lng: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = weatherRepository.isCityFavorite(lat, lng)
            _isCityFavorite.postValue(response)
        }
    }
}