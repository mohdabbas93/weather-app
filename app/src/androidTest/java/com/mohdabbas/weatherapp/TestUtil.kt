package com.mohdabbas.weatherapp

import com.mohdabbas.weatherapp.data.source.local.CityWeather
import com.mohdabbas.weatherapp.data.source.local.entity.DailyWeather
import com.mohdabbas.weatherapp.data.source.local.entity.relation.CityWeatherWithDailyWeathers

/**
 * Created by Mohammad Abbas
 * On: 1/5/22.
 */
object TestUtil {
    fun createCityWeather(cityWeatherId: Int = 1, isDefault: Boolean) = CityWeatherWithDailyWeathers(
        cityWeather = CityWeather(
            id = cityWeatherId,
            lat = 22.0,
            lng = 34.0,
            timezone = "Cairo",
            currentUTCTime = 123456,
            temperature = 34.0,
            feelsLike = 36.0,
            pressure = 1000,
            humidity = 40,
            windSpeed = 13.0,
            weatherCondition = "Sunny",
            weatherConditionIcon = "02d",
            isDefault = isDefault
        ),
        dailyWeathers = listOf(
            DailyWeather(
                id = (cityWeatherId - 1) * 2 + 1,
                cityWeatherId = cityWeatherId,
                currentUTCTime = 12345,
                24.0,
                29.0,
                "Sunny",
                "02d"
            ),
            DailyWeather(
                id = (cityWeatherId - 1) * 2 + 2,
                cityWeatherId = cityWeatherId,
                currentUTCTime = 12345,
                25.0,
                29.0,
                "Cloudy",
                "02d"
            )
        )
    )
}