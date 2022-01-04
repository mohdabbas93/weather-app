package com.mohdabbas.weatherapp.data.source.local

import com.mohdabbas.weatherapp.data.Result
import com.mohdabbas.weatherapp.data.source.WeatherDataSource
import com.mohdabbas.weatherapp.data.source.remote.dto.CityWeatherDto
import com.mohdabbas.weatherapp.data.source.remote.dto.CurrentWeatherDto
import com.mohdabbas.weatherapp.data.source.remote.dto.WeatherDto
import com.mohdabbas.weatherapp.util.ErrorType

/**
 * Created by Mohammad Abbas
 * On: 1/1/22.
 */
class WeatherLocalDataSource(
    private val weatherDao: WeatherDao
) : WeatherDataSource {
    override suspend fun getCurrentLocationWeatherData(): Result<CityWeatherDto> {
        return try {
            Result.Success(weatherDao.getWeatherData().first().toDto())
        } catch (e: Exception) {
            Result.Error(e, ErrorType.NoSavedData)
        }
    }

    override suspend fun getWeatherData(lat: Double, lng: Double): Result<CityWeatherDto> {
        TODO("Not yet implemented")
    }

    override suspend fun addWeatherData(cityWeatherDto: CityWeatherDto) {
        weatherDao.addWeatherData(cityWeatherDto.toEntity())
    }

    private fun CityWeatherDto.toEntity() = CityWeather(
        1,
        lat,
        lng,
        timezone,
        currentWeather.currentUTCTime,
        currentWeather.temperature,
        currentWeather.feelsLike,
        currentWeather.pressure,
        currentWeather.humidity,
        currentWeather.windSpeed,
        currentWeather.weather.firstOrNull()?.weatherCondition ?: "",
        currentWeather.weather.firstOrNull()?.icon ?: ""
    )

    private fun CityWeather.toDto() = CityWeatherDto(
        lat,
        lng,
        timezone,
        CurrentWeatherDto(
            currentUTCTime,
            temperature,
            feelsLike,
            pressure,
            humidity,
            windSpeed,
            listOf(
                WeatherDto(

                    weatherCondition ?: "",
                    weatherConditionIcon ?: ""
                )
            ),
        ),
        listOf(),
        listOf()
    )

    override suspend fun getFavoriteCities(): List<FavoriteCity> {
        return weatherDao.getFavoriteCities()
    }
}