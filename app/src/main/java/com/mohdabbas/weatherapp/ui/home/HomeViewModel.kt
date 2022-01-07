package com.mohdabbas.weatherapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohdabbas.weatherapp.data.Result
import com.mohdabbas.weatherapp.data.source.WeatherRepository
import com.mohdabbas.weatherapp.data.source.remote.dto.CityWeatherDto
import com.mohdabbas.weatherapp.persistence.PersistenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Mohammad Abbas
 * On: 1/2/22.
 */
class HomeViewModel(
    private val weatherRepository: WeatherRepository,
    private val persistenceManager: PersistenceManager
) : ViewModel() {

    fun isCelsius() = persistenceManager.isCelsius

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

    private val _locationUnavailable = MutableLiveData<Boolean>()
    val locationUnavailable: LiveData<Boolean> = _locationUnavailable

    fun setLocationUnavailable() {
        _locationUnavailable.value = true
    }

    private val _weatherData = MutableLiveData<Result<CityWeatherDto>>()
    val weatherData: LiveData<Result<CityWeatherDto>> = _weatherData

    fun getWeatherData(
        lat: Double,
        lng: Double,
        hasLoading: Boolean = true,
        storeInDb: Boolean = true,
        isDefault: Boolean = true,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (hasLoading) {
                _loading.postValue(true)
            }
            val response =
                weatherRepository.getRemoteWeatherDataAndStoreItInDb(lat, lng, isDefault, storeInDb)
            _loading.postValue(false)
            _weatherData.postValue(response)
        }
    }

    private val _localFavoriteCityWeatherData = MutableLiveData<Result<CityWeatherDto>>()
    val localFavoriteCityWeatherData: LiveData<Result<CityWeatherDto>> =
        _localFavoriteCityWeatherData

    fun getFavoriteCityWeatherData(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            val result = weatherRepository.getCityWeather(id)
            _localFavoriteCityWeatherData.postValue(result)
            _loading.postValue(false)
        }
    }

    fun addFavoriteCity() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = weatherData.value
            if (data is Result.Success) {
                val result = weatherRepository.addWeatherData(data.data, isDefault = false)
                if (result != null) cityWeatherId = result
            }
        }
    }

    var cityWeatherId: Int = 0

    fun deleteFavoriteCity() {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.deleteCityWeather(cityWeatherId)
        }
    }
}