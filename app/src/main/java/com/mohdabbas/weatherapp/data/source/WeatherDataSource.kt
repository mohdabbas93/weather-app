package com.mohdabbas.weatherapp.data.source

import com.mohdabbas.weatherapp.data.source.local.FavoriteCity
import com.mohdabbas.weatherapp.data.source.remote.dto.CityWeatherDto

/**
 * Created by Mohammad Abbas
 * On: 1/2/22.
 */

/**
 * Main entry points for accessing weather data.
 */
interface WeatherDataSource {
    suspend fun getWeatherData(lat: Double, lng: Double): CityWeatherDto
    suspend fun addWeatherData(cityWeatherDto: CityWeatherDto)
    suspend fun getFavoriteCities(): List<FavoriteCity>
}