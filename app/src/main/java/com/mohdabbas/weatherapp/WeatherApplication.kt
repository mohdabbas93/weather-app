package com.mohdabbas.weatherapp

import android.app.Application
import androidx.room.Room
import com.mohdabbas.weatherapp.data.source.CitySearchRepository
import com.mohdabbas.weatherapp.data.source.WeatherRepository
import com.mohdabbas.weatherapp.data.source.local.WeatherDatabase
import com.mohdabbas.weatherapp.data.source.local.WeatherLocalDataSource
import com.mohdabbas.weatherapp.data.source.remote.WeatherApi
import com.mohdabbas.weatherapp.data.source.remote.WeatherRemoteDataSource
import com.mohdabbas.weatherapp.data.source.remote.citysearch.CitySearchApi
import com.mohdabbas.weatherapp.data.source.remote.citysearch.CitySearchRemoteDataSource
import com.mohdabbas.weatherapp.persistence.PersistenceManager
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Mohammad Abbas
 * On: 1/2/22.
 */
@HiltAndroidApp
class WeatherApplication : Application() {

    companion object {
        lateinit var persistenceManager: PersistenceManager
        lateinit var weatherRepository: WeatherRepository
        lateinit var citySearchRepository: CitySearchRepository
    }

    override fun onCreate() {
        super.onCreate()

        persistenceManager = PersistenceManager(applicationContext)

        val weatherRemoteDataSource = WeatherRemoteDataSource(WeatherApi.create())
        val database = Room.databaseBuilder(this, WeatherDatabase::class.java, "weather-db").build()
        val weatherLocalDataSource = WeatherLocalDataSource(database.weatherDao())

        weatherRepository = WeatherRepository(weatherRemoteDataSource, weatherLocalDataSource)

        val citySearchRemoteDataSource = CitySearchRemoteDataSource(CitySearchApi.create())
        citySearchRepository = CitySearchRepository(citySearchRemoteDataSource)
    }
}