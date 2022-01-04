package com.mohdabbas.weatherapp.data.source.remote

import com.mohdabbas.weatherapp.data.Result
import com.mohdabbas.weatherapp.data.source.WeatherDataSource
import com.mohdabbas.weatherapp.data.source.local.FavoriteCity
import com.mohdabbas.weatherapp.data.source.remote.dto.CityWeatherDto

/**
 * Created by Mohammad Abbas
 * On: 1/2/22.
 */
class WeatherRemoteDataSource(
    private val weatherApi: WeatherApi
) : WeatherDataSource {
    override suspend fun getWeatherData(lat: Double, lng: Double): Result<CityWeatherDto> {
        return try {
            Result.Success(weatherApi.getCityWeatherData(lat, lng))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun addWeatherData(cityWeatherDto: CityWeatherDto) {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteCities(): List<FavoriteCity> {
        TODO("Not yet implemented")
    }
}