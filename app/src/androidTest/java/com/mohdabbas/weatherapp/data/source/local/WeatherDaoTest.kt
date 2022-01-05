package com.mohdabbas.weatherapp.data.source.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.mohdabbas.weatherapp.TestUtil
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Mohammad Abbas
 * On: 1/2/22.
 */
@RunWith(AndroidJUnit4::class)
@SmallTest
class WeatherDaoTest {

    private lateinit var database: WeatherDatabase
    private lateinit var dao: WeatherDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.weatherDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertCityWeather() = runBlocking {
        val cityWeather = CityWeather(
            1, 24.5, 53.4, "Cairo", 12345678, 34.0, 34.9,
            1080, 40, 1234.0, "Sunny", "0d2"
        )

        dao.addWeatherData(cityWeather)

        val citiesWeathers = dao.getWeatherData()

        assert(citiesWeathers.contains(cityWeather))
    }

    @Test
    fun addFavoriteCity() = runBlocking {
        val favCity =
            FavoriteCity(1, 24.5, 53.4, 34.0, "Sunny", "0d2", 13.0, 34, 1234.0)

        dao.addFavoriteCity(favCity)

        val favCities = dao.getFavoriteCities()

        assert(favCities.contains(favCity))
    }

    @Test
    fun deleteFavoriteCity() = runBlocking {
        val favCity =
            FavoriteCity(1, 24.5, 53.4, 34.0, "Sunny", "0d2", 13.0, 34, 1234.0)

        dao.addFavoriteCity(favCity)
        dao.deleteFavoriteCity(favCity)

        val favCities = dao.getFavoriteCities()

        assert(!favCities.contains(favCity))
    }

    @Test
    fun addAndGetDefaultCityWeatherData() = runBlocking {
        val defaultCityWeather = TestUtil.createCityWeather(isDefault = true)

        dao.addWeatherData(defaultCityWeather.cityWeather, defaultCityWeather.dailyWeathers)
        val result = dao.getDefaultCityWeatherData()

        assertEquals(defaultCityWeather, result)
    }
}