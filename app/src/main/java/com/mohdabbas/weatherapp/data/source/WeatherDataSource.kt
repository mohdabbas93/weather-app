package com.mohdabbas.weatherapp.data.source

import com.mohdabbas.weatherapp.data.Result
import com.mohdabbas.weatherapp.data.source.local.CityWeather
import com.mohdabbas.weatherapp.data.source.remote.dto.CityWeatherDto

/**
 * Created by Mohammad Abbas
 * On: 1/2/22.
 */

/**
 * Main entry points for accessing weather data.
 */
interface WeatherDataSource {
    suspend fun getCurrentLocationWeatherData(): Result<CityWeatherDto>
    suspend fun getWeatherData(lat: Double, lng: Double): Result<CityWeatherDto>
    suspend fun addWeatherData(cityWeatherDto: CityWeatherDto)
    suspend fun getFavoriteCities(): List<CityWeather>
    suspend fun isCityFavorite(lat: Double, lng: Double): Boolean
}