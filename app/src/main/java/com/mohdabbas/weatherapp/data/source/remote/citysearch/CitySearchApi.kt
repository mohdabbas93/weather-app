package com.mohdabbas.weatherapp.data.source.remote.citysearch

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Mohammad Abbas
 * On: 1/5/22.
 */
interface CitySearchApi {

    @GET("places?type=CITY")
    suspend fun searchCity(
        @Query("q") searchTerm: String,
        @Query("limit") limit: Int = 10
    ): List<CitySearchDto>

    companion object {
        private const val BASE_URL = "https://spott.p.rapidapi.com/"
        private const val RABID_API_HOST = "spott.p.rapidapi.com"
        private const val RABID_API_KEY = "7db8c61634msh25ab67e1d3748c8p1f04bejsn47b6d80dce02"

        fun create(): CitySearchApi {
            val logger = HttpLoggingInterceptor { Log.d("API", it) }
            logger.level = HttpLoggingInterceptor.Level.BODY

            val headers = Interceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                requestBuilder.header("x-rapidapi-host", RABID_API_HOST)
                requestBuilder.header("x-rapidapi-key", RABID_API_KEY)
                return@Interceptor chain.proceed(requestBuilder.build())
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .addInterceptor(headers)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CitySearchApi::class.java)
        }
    }
}