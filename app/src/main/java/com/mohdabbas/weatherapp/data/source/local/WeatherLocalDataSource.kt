package com.mohdabbas.weatherapp.data.source.local

import com.mohdabbas.weatherapp.data.Result
import com.mohdabbas.weatherapp.data.source.WeatherDataSource
import com.mohdabbas.weatherapp.data.source.local.entity.DailyWeather
import com.mohdabbas.weatherapp.data.source.local.entity.relation.CityWeatherWithDailyWeathers
import com.mohdabbas.weatherapp.data.source.remote.dto.*
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
            Result.Success(weatherDao.getDefaultCityWeatherData().toCityWeatherDto())
        } catch (e: Exception) {
            Result.Error(e, ErrorType.NoSavedData)
        }
    }

    override suspend fun getRemoteWeatherDataAndStoreItInDb(
        lat: Double,
        lng: Double,
        isDefault: Boolean,
        storeInDb: Boolean
    ): Result<CityWeatherDto> {
        TODO("Not yet implemented")
    }

    override suspend fun addWeatherData(cityWeatherDto: CityWeatherDto, isDefault: Boolean) {
        weatherDao.addWeatherData(
            cityWeatherDto.toCityWeather(isDefault),
            cityWeatherDto.dailyWeather.toDailyWeather(),
            isDefault
        )
    }

    private fun CityWeatherDto.toCityWeather(isDefault: Boolean) = CityWeather(
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
        currentWeather.weather.firstOrNull()?.icon ?: "",
        isDefault
    )

    private fun List<DailyWeatherDto>.toDailyWeather() = map {
        DailyWeather(
            id = null,
            cityWeatherId = 1, // Default number and will be changed later when actual id generated from insert of city weather
            currentUTCTime = it.currentUTCTime,
            minTemperature = it.temperature.minTemperature,
            maxTemperature = it.temperature.maxTemperature,
            weatherCondition = it.weather.firstOrNull()?.weatherCondition ?: "",
            weatherConditionIcon = it.weather.firstOrNull()?.icon ?: ""
        )
    }

    private fun CityWeatherWithDailyWeathers.toCityWeatherDto() = CityWeatherDto(
        cityWeather.lat,
        cityWeather.lng,
        cityWeather.timezone,
        CurrentWeatherDto(
            cityWeather.currentUTCTime,
            cityWeather.temperature,
            cityWeather.feelsLike,
            cityWeather.pressure,
            cityWeather.humidity,
            cityWeather.windSpeed,
            listOf(
                WeatherDto(

                    cityWeather.weatherCondition,
                    cityWeather.weatherConditionIcon
                )
            ),
        ),
        listOf(),
        dailyWeathers.toDailyWeatherDto()
    )

    private fun List<DailyWeather>.toDailyWeatherDto() = map {
        DailyWeatherDto(
            currentUTCTime = it.currentUTCTime,
            weather = listOf(
                WeatherDto(
                    weatherCondition = it.weatherCondition,
                    icon = it.weatherConditionIcon
                )
            ),
            temperature = TemperatureDto(
                minTemperature = it.minTemperature,
                maxTemperature = it.maxTemperature
            )
        )
    }

    override suspend fun getFavoriteCities(): List<CityWeather> {
        return weatherDao.getAllFavoriteCities()
    }

    override suspend fun isCityFavorite(lat: Double, lng: Double): Boolean {
        return weatherDao.isCityFavoriteExist(lat.toInt(), lng.toInt()) > 0
    }
}