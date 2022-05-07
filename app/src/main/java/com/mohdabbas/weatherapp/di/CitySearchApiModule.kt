package com.mohdabbas.weatherapp.di

import android.util.Log
import com.mohdabbas.weatherapp.data.source.remote.citysearch.CitySearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Mohammad Abbas
 * On: 5/8/22.
 */
@Module
@InstallIn(SingletonComponent::class)
object CitySearchApiModule {
    private const val BASE_URL = "https://spott.p.rapidapi.com/"
    private const val RABID_API_HOST = "spott.p.rapidapi.com"
    private const val RABID_API_KEY = "7db8c61634msh25ab67e1d3748c8p1f04bejsn47b6d80dce02"

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() =
        HttpLoggingInterceptor { Log.d("City Search API", it) }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesHeadersInterceptor() = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.header("x-rapidapi-host", RABID_API_HOST)
        requestBuilder.header("x-rapidapi-key", RABID_API_KEY)
        return@Interceptor chain.proceed(requestBuilder.build())
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        headersInterceptor: Interceptor
    ) =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(headersInterceptor)
            .build()

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): CitySearchApi = retrofit.create(CitySearchApi::class.java)
}