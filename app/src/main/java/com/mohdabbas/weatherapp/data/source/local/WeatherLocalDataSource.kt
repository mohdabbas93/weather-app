package com.mohdabbas.weatherapp.data.source.local

import com.mohdabbas.weatherapp.data.source.WeatherDataSource
import com.mohdabbas.weatherapp.data.source.remote.dto.CityWeatherDto

/**
 * Created by Mohammad Abbas
 * On: 1/1/22.
 */
class WeatherLocalDataSource(
    private val weatherDao: WeatherDao
) : WeatherDataSource {
    override suspend fun getWeatherData(lat: Double, lng: Double): CityWeatherDto {
        TODO("Not yet implemented")

    }
}