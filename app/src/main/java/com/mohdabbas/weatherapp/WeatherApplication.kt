package com.mohdabbas.weatherapp

import android.app.Application
import androidx.room.Room
import com.mohdabbas.weatherapp.data.source.WeatherRepository
import com.mohdabbas.weatherapp.data.source.local.WeatherDatabase
import com.mohdabbas.weatherapp.data.source.local.WeatherLocalDataSource
import com.mohdabbas.weatherapp.data.source.remote.WeatherApi
import com.mohdabbas.weatherapp.data.source.remote.WeatherRemoteDataSource

/**
 * Created by Mohammad Abbas
 * On: 1/2/22.
 */
class WeatherApplication : Application() {

    companion object {
        lateinit var WeatherRepository: WeatherRepository
    }

    override fun onCreate() {
        super.onCreate()

        val weatherRemoteDataSource = WeatherRemoteDataSource(WeatherApi.create())
        val database = Room.databaseBuilder(this, WeatherDatabase::class.java, "weather-db").build()
        val weatherLocalDataSource = WeatherLocalDataSource(database.weatherDao())

        WeatherRepository = WeatherRepository(weatherRemoteDataSource, weatherLocalDataSource)
    }
}