package com.mohdabbas.weatherapp.ui.favcities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohdabbas.weatherapp.data.source.WeatherRepository
import com.mohdabbas.weatherapp.data.source.local.CityWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Mohammad Abbas
 * On: 1/3/22.
 */
class FavoriteCitiesViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _favoriteCities = MutableLiveData<List<CityWeather>>()
    val favoriteCities: LiveData<List<CityWeather>> = _favoriteCities

    fun getFavoriteCities() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            val response = weatherRepository.getFavoriteCities()
            _loading.postValue(false)
            _favoriteCities.postValue(response)
        }
    }
}