package com.mohdabbas.weatherapp.data.source.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by Mohammad Abbas
 * On: 1/1/22.
 */

data class CityWeatherDto(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lng: Double,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("current")
    val currentWeather: CurrentWeatherDto,
    @SerializedName("hourly")
    val hourlyWeather: List<HourlyWeatherDto>,
    @SerializedName("daily")
    val dailyWeather: List<DailyWeatherDto>
)

data class CurrentWeatherDto(
    @SerializedName("dt")
    val currentUTCTime: Long,
    @SerializedName("temp")
    val temperature: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("pressure")
    val pressure: Long,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("weather")
    val weather: List<WeatherDto>
)

data class WeatherDto(
    @SerializedName("main")
    val weatherCondition: String,
    @SerializedName("icon")
    val icon: String
)

data class HourlyWeatherDto(
    @SerializedName("dt")
    val currentUTCTime: Long,
    @SerializedName("temp")
    val temperature: Double,
    @SerializedName("humidity")
    val humidity: Double,
    @SerializedName("weather")
    val weather: List<WeatherDto>
)

data class DailyWeatherDto(
    @SerializedName("dt")
    val currentUTCTime: Long,
    @SerializedName("weather")
    val weather: List<WeatherDto>,
    @SerializedName("temp")
    val temperature: TemperatureDto
)

data class TemperatureDto(
    @SerializedName("min")
    val minTemperature: Double,
    @SerializedName("max")
    val maxTemperature: Double
)