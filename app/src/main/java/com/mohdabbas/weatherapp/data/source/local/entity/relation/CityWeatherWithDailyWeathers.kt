package com.mohdabbas.weatherapp.data.source.local.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.mohdabbas.weatherapp.data.source.local.CityWeather
import com.mohdabbas.weatherapp.data.source.local.entity.DailyWeather

/**
 * Created by Mohammad Abbas
 * On: 1/4/22.
 */
data class CityWeatherWithDailyWeathers(
    @Embedded
    val cityWeather: CityWeather,
    @Relation(
        parentColumn = "id",
        entityColumn = "city_weather_id"
    )
    val dailyWeathers: List<DailyWeather>
)