package com.mohdabbas.weatherapp.data.source.local

import androidx.room.*

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

    @Query("SELECT * FROM favorite_cities")
    suspend fun getFavoriteCities(): List<FavoriteCity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteCity(favoriteCityWeather: FavoriteCity)

    @Delete
    suspend fun deleteFavoriteCity(favoriteCityWeather: FavoriteCity)
}