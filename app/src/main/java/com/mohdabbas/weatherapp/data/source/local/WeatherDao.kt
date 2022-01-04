package com.mohdabbas.weatherapp.data.source.local

import androidx.room.*
import com.mohdabbas.weatherapp.data.source.local.entity.DailyWeather
import com.mohdabbas.weatherapp.data.source.local.entity.relation.CityWeatherWithDailyWeathers

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

    @Transaction
    suspend fun addWeatherData(cityWeather: CityWeather, dailyWeather: List<DailyWeather>) {
        addCityWeatherData(cityWeather)
        addDailyWeathersData(dailyWeather)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCityWeatherData(cityWeather: CityWeather)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDailyWeathersData(dailyWeather: List<DailyWeather>)

    @Query("SELECT * FROM city_weather WHERE id = :cityWeatherId")
    suspend fun getWeatherDataById(cityWeatherId: Int): CityWeatherWithDailyWeathers

    @Query("SELECT * FROM favorite_cities")
    suspend fun getFavoriteCities(): List<FavoriteCity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteCity(favoriteCityWeather: FavoriteCity)

    @Delete
    suspend fun deleteFavoriteCity(favoriteCityWeather: FavoriteCity)
}