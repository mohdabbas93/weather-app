package com.mohdabbas.weatherapp.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Mohammad Abbas
 * On: 1/1/22.
 */
@Entity(tableName = "favorite_cities")
data class FavoriteCity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val lat: Double,
    val lng: Double,
    @ColumnInfo(name = "current_temperature")
    val currentTemperature: Double,
    @ColumnInfo(name = "current_condition")
    val weatherCondition: String,
    @ColumnInfo(name = "current_condition_icon")
    val weatherConditionIcon: String,
    @ColumnInfo(name = "wind_speed")
    val windSpeed: Double,
    val humidity: Int,
    val pressure: Double
)