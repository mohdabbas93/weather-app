package com.mohdabbas.weatherapp.data.source.remote

import com.mohdabbas.weatherapp.data.Result
import com.mohdabbas.weatherapp.data.source.WeatherDataSource
import com.mohdabbas.weatherapp.data.source.local.CityWeather
import com.mohdabbas.weatherapp.data.source.remote.dto.CityWeatherDto
import com.mohdabbas.weatherapp.util.ErrorType

/**
 * Created by Mohammad Abbas
 * On: 1/2/22.
 */
class WeatherRemoteDataSource(
    private val weatherApi: WeatherApi
) : WeatherDataSource {
    override suspend fun getCurrentLocationWeatherData(): Result<CityWeatherDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getCityWeather(id: Int): Result<CityWeatherDto> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCityWeather(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getRemoteWeatherDataAndStoreItInDb(
        lat: Double,
        lng: Double,
        isDefault: Boolean,
        storeInDb: Boolean
    ): Result<CityWeatherDto> {
        return try {
            Result.Success(weatherApi.getCityWeatherData(lat, lng))
        } catch (e: Exception) {
            Result.Error(e, ErrorType.RemoteError)
        }
    }

    override suspend fun addWeatherData(cityWeatherDto: CityWeatherDto, isDefault: Boolean): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteCities(): List<CityWeather> {
        TODO("Not yet implemented")
    }

    override suspend fun isCityFavorite(lat: Double, lng: Double): Boolean {
        TODO("Not yet implemented")
    }
}