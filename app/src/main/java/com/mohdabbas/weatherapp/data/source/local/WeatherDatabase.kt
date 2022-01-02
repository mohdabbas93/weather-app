package com.mohdabbas.weatherapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by Mohammad Abbas
 * On: 1/1/22.
 */
@Database(entities = [CityWeather::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}