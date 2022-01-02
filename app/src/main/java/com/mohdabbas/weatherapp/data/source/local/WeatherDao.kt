package com.mohdabbas.weatherapp.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by Mohammad Abbas
 * On: 1/1/22.
 */
@Dao
interface WeatherDao {
    @Query("SELECT * FROM city_weather")
    suspend fun getWeatherData(): List<CityWeather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWeatherData(cityWeather: CityWeather)
}