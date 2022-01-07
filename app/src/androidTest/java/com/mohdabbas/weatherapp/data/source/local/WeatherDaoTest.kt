package com.mohdabbas.weatherapp.data.source.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.mohdabbas.weatherapp.TestUtil
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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
    fun addAndGetDefaultCityWeatherData() = runBlocking {
        val defaultCityWeather = TestUtil.createCityWeather(isDefault = true)

        dao.addWeatherData(
            defaultCityWeather.cityWeather,
            defaultCityWeather.dailyWeathers,
            isDefault = false
        )
        val result = dao.getDefaultCityWeatherData()

        assertEquals(defaultCityWeather, result)
    }

    @Test
    fun addAndGetFavoriteCityWeatherData() = runBlocking {
        val favCityWeather = TestUtil.createCityWeather(isDefault = false)

        dao.addWeatherData(
            favCityWeather.cityWeather,
            favCityWeather.dailyWeathers,
            isDefault = false
        )
        val result = dao.getFavoriteCitiesWeathersData()

        assertTrue(result.contains(favCityWeather))
    }

    @Test
    fun addAndGetMultipleFavoriteCitiesWeathersData() = runBlocking {
        val favCityWeather1 = TestUtil.createCityWeather(cityWeatherId = 1, isDefault = false)
        val favCityWeather2 = TestUtil.createCityWeather(cityWeatherId = 2, isDefault = false)
        val favCityWeather3 = TestUtil.createCityWeather(cityWeatherId = 3, isDefault = false)


        dao.addWeatherData(
            favCityWeather1.cityWeather,
            favCityWeather1.dailyWeathers,
            isDefault = false
        )
        dao.addWeatherData(
            favCityWeather2.cityWeather,
            favCityWeather2.dailyWeathers,
            isDefault = false
        )
        dao.addWeatherData(
            favCityWeather3.cityWeather,
            favCityWeather3.dailyWeathers,
            isDefault = false
        )

        val result = dao.getFavoriteCitiesWeathersData()
        val favCitiesWeathers = listOf(favCityWeather1, favCityWeather2, favCityWeather3)

        assertTrue(result.containsAll(favCitiesWeathers))
    }

    @Test
    fun addAndDeleteFavoriteCityWeatherDataById() = runBlocking {
        val favCityWeather = TestUtil.createCityWeather(cityWeatherId = 1, isDefault = false)

        dao.addWeatherData(
            favCityWeather.cityWeather,
            favCityWeather.dailyWeathers,
            isDefault = false
        )
        dao.deleteFavoriteCityWeatherDataById(favCityWeather.cityWeather.id!!)
        val result = dao.getFavoriteCitiesWeathersData()

        assertTrue(!result.contains(favCityWeather))
    }
}