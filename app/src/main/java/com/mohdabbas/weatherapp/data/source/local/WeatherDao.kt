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
    suspend fun getWeatherData(): List<CityWeatherWithDailyWeathers>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWeatherData(cityWeather: CityWeather)

    @Query("SELECT * FROM city_weather WHERE id = :cityWeatherId")
    suspend fun getWeatherDataById(cityWeatherId: Int): CityWeatherWithDailyWeathers

    @Query("SELECT * FROM favorite_cities")
    suspend fun getFavoriteCities(): List<FavoriteCity>

    @Query("SELECT * FROM city_weather WHERE is_default = 0")
    suspend fun getAllFavoriteCities(): List<CityWeather>

    // TODO: Test this later
    @Query("SELECT Count(*) FROM city_weather WHERE Cast(lat AS int) = :lat and Cast(lng AS int) = :lng")
    suspend fun isCityFavoriteExist(lat: Int, lng: Int): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteCity(favoriteCityWeather: FavoriteCity)

    @Delete
    suspend fun deleteFavoriteCity(favoriteCityWeather: FavoriteCity)

    // CRUD (Only tow tables needed, city_weather and daily_weather)

    @Transaction
    suspend fun addWeatherData(
        cityWeather: CityWeather,
        dailyWeather: List<DailyWeather>,
        isDefault: Boolean
    ) {
        val id = getCityWeatherId(cityWeather.lat.toInt(), cityWeather.lng.toInt())?.toInt()
        val updatedCityWeather = if (id == null) cityWeather else cityWeather.copy(id = id)
        val cityWeatherId = addCityWeatherData(updatedCityWeather).toInt()
        deleteDailyCityWeatherById(cityWeatherId)
        val updatedDailyWeather =
            dailyWeather.map { it.copy(cityWeatherId = cityWeatherId) }
        addDailyWeathersData(updatedDailyWeather)
    }

    @Query("SELECT id FROM city_weather WHERE  Cast(lat AS int) = :lat and Cast(lng AS int) = :lng")
    suspend fun getCityWeatherId(lat: Int, lng: Int): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDailyWeathersData(dailyWeather: List<DailyWeather>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCityWeatherData(cityWeather: CityWeather): Long

    @Transaction
    @Query("SELECT * FROM city_weather WHERE is_default = 1")
    suspend fun getDefaultCityWeatherData(): CityWeatherWithDailyWeathers

    @Transaction
    @Query("SELECT * FROM city_weather WHERE is_default = 0")
    suspend fun getFavoriteCitiesWeathersData(): List<CityWeatherWithDailyWeathers>

    // Update

    @Transaction
    suspend fun deleteFavoriteCityWeatherDataById(cityWeatherId: Int) {
        deleteFavoriteCityById(cityWeatherId)
        deleteDailyCityWeatherById(cityWeatherId)
    }

    @Query("DELETE FROM city_weather WHERE id = :id ")
    suspend fun deleteFavoriteCityById(id: Int)

    @Query("DELETE FROM daily_weather WHERE city_weather_id = :cityWeatherId")
    suspend fun deleteDailyCityWeatherById(cityWeatherId: Int)
}