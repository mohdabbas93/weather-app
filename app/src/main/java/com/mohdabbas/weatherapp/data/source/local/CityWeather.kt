package com.mohdabbas.weatherapp.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Mohammad Abbas
 * On: 1/1/22.
 */
@Entity(tableName = "city_weather")
data class CityWeather(
    @PrimaryKey
    val id: Int,
    val lat: Double,
    val lng: Double,
    val timezone: String,
    @ColumnInfo(name = "current_utc_time")
    val currentUTCTime: Long,
    val temperature: Double,
    @ColumnInfo(name = "feels_like")
    val feelsLike: Double,
    val pressure: Long,
    val humidity: Int,
    @ColumnInfo(name = "wind_speed")
    val windSpeed: Double,
    @ColumnInfo(name = "weather_condition")
    val weatherCondition: String,
    @ColumnInfo(name = "weather_condition_icon")
    val weatherConditionIcon: String,
    @ColumnInfo(name = "is_default")
    val isDefault: Boolean = false
)