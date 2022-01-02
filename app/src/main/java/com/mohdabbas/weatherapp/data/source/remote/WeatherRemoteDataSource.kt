package com.mohdabbas.weatherapp.data.source.remote

import com.mohdabbas.weatherapp.data.source.WeatherDataSource
import com.mohdabbas.weatherapp.data.source.remote.dto.CityWeatherDto

/**
 * Created by Mohammad Abbas
 * On: 1/2/22.
 */
class WeatherRemoteDataSource(
    private val weatherApi: WeatherApi
) : WeatherDataSource {
    override suspend fun getWeatherData(lat: Double, lng: Double): CityWeatherDto {
        return weatherApi.getCityWeatherData(lat, lng)
    }
}