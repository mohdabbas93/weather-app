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

    private val _localWeatherData = MutableLiveData<Result<CityWeatherDto>>()
    val localWeatherData: LiveData<Result<CityWeatherDto>> = _localWeatherData

    fun getCurrentLocationWeatherData() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            val response = weatherRepository.getCurrentLocationWeatherData()
            _loading.postValue(false)
            _localWeatherData.postValue(response)
        }
    }

    fun setLoading() {
        _loading.value = true
    }

    private val _weatherData = MutableLiveData<Result<CityWeatherDto>>()
    val weatherData: LiveData<Result<CityWeatherDto>> = _weatherData

    fun getWeatherData(
        lat: Double,
        lng: Double,
        hasLoading: Boolean = true,
        storeInDb: Boolean = true
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (hasLoading) {
                _loading.postValue(true)
            }
            val response =
                weatherRepository.getRemoteWeatherDataAndStoreItInDb(lat, lng, true, storeInDb)
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

    fun addFavoriteCity() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = weatherData.value
            if (result is Result.Success) {
                weatherRepository.addWeatherData(result.data, isDefault = false)
            }
        }
    }
}