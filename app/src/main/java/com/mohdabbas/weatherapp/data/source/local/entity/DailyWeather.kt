package com.mohdabbas.weatherapp.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Mohammad Abbas
 * On: 1/4/22.
 */
@Entity(tableName = "daily_weather")
data class DailyWeather(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "current_utc_time")
    val currentUTCTime: Long,
    @ColumnInfo(name = "min_temperature")
    val minTemperature: Double,
    @ColumnInfo(name = "max_temperature")
    val maxTemperature: Double,
    @ColumnInfo(name = "weather_condition")
    val weatherCondition: String,
    @ColumnInfo(name = "weather_condition_icon")
    val weatherConditionIcon: String,
)