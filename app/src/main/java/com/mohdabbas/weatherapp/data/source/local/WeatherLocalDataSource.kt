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

    override suspend fun addWeatherData(cityWeatherDto: CityWeatherDto) {
        weatherDao.addWeatherData(cityWeatherDto.toEntity())
    }

    private fun CityWeatherDto.toEntity() = CityWeather(
        null,
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

    override suspend fun getFavoriteCities(): List<FavoriteCity> {
        TODO("Not yet implemented")
    }
}