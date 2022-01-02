package com.mohdabbas.weatherapp.ui.favcities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohdabbas.weatherapp.data.source.WeatherRepository
import com.mohdabbas.weatherapp.data.source.local.FavoriteCity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Mohammad Abbas
 * On: 1/3/22.
 */
class FavoriteCitiesViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _favoriteCities = MutableLiveData<List<FavoriteCity>>()
    val favoriteCities: LiveData<List<FavoriteCity>> = _favoriteCities

    fun getFavoriteCities() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = weatherRepository.getFavoriteCities()
            _favoriteCities.postValue(response)
        }
    }
}