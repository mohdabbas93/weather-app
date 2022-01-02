package com.mohdabbas.weatherapp.data.source

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
    private val remoteDataSource: WeatherRemoteDataSource
) : WeatherDataSource {
    override suspend fun getWeatherData(lat: Double, lng: Double): CityWeatherDto {
        return remoteDataSource.getWeatherData(lat, lng)
    }
}