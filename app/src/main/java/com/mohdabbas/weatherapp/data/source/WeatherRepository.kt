package com.mohdabbas.weatherapp.data.source

import com.mohdabbas.weatherapp.data.Result
import com.mohdabbas.weatherapp.data.source.local.FavoriteCity
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
    override suspend fun getWeatherData(lat: Double, lng: Double): Result<CityWeatherDto> {
        val remoteResult = remoteDataSource.getWeatherData(lat, lng)
        if (remoteResult is Result.Success) {
            localDataSource.addWeatherData(remoteResult.data)
        }
        return remoteResult
    }

    override suspend fun addWeatherData(cityWeatherDto: CityWeatherDto) {
        localDataSource.addWeatherData(cityWeatherDto)
    }

    override suspend fun getFavoriteCities(): List<FavoriteCity> {
        return localDataSource.getFavoriteCities()
    }
}