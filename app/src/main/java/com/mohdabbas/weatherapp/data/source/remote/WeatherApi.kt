package com.mohdabbas.weatherapp.data.source.remote

import android.util.Log
import com.mohdabbas.weatherapp.data.source.remote.dto.CityWeatherDto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Mohammad Abbas
 * On: 1/2/22.
 */
interface WeatherApi {
    @GET("onecall?exclude=minutely&units=metric&appid=$API_KEY")
    suspend fun getCityWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lng: Double
    ): CityWeatherDto

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        private const val API_KEY = "f908845614434e18d389e8a0760e100c"

        fun create(): WeatherApi {
            val logger = HttpLoggingInterceptor { Log.d("API", it) }
            logger.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)
        }
    }
}