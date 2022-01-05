package com.mohdabbas.weatherapp.data.source

import com.mohdabbas.weatherapp.data.Result
import com.mohdabbas.weatherapp.data.source.local.CityWeather
import com.mohdabbas.weatherapp.data.source.remote.WeatherRemoteDataSource
import com.mohdabbas.weatherapp.data.source.remote.dto.CityWeatherDto

/**
 * Created by Mohammad Abbas
 * On: 1/2/22.
 */

/**
 * Single entry point for managing weather data
 */
class WeatherRepository(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: WeatherDataSource
) : WeatherDataSource {

    override suspend fun getCurrentLocationWeatherData(): Result<CityWeatherDto> {
        return localDataSource.getCurrentLocationWeatherData()
    }

    override suspend fun getWeatherData(lat: Double, lng: Double): Result<CityWeatherDto> {
        val remoteResult = remoteDataSource.getWeatherData(lat, lng)
        if (remoteResult is Result.Success) {
            // addWeatherData(remoteResult.data)
        }
        // TODO: Change this later
        return remoteResult
    }

    override suspend fun addWeatherData(cityWeatherDto: CityWeatherDto, isDefault: Boolean) {
        localDataSource.addWeatherData(cityWeatherDto, isDefault)
    }

    override suspend fun getFavoriteCities(): List<CityWeather> {
        return localDataSource.getFavoriteCities()
    }

    override suspend fun isCityFavorite(lat: Double, lng: Double): Boolean {
        return localDataSource.isCityFavorite(lat, lng)
    }
}