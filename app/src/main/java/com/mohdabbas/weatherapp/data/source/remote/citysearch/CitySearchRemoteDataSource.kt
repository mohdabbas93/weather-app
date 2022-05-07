package com.mohdabbas.weatherapp.data.source.remote.citysearch

import com.mohdabbas.weatherapp.data.Result
import com.mohdabbas.weatherapp.data.source.CitySearchDataSource
import com.mohdabbas.weatherapp.util.ErrorType
import javax.inject.Inject

/**
 * Created by Mohammad Abbas
 * On: 1/5/22.
 */
class CitySearchRemoteDataSource @Inject constructor(
    private val citySearchApi: CitySearchApi
) : CitySearchDataSource {

    override suspend fun searchCity(searchTerm: String): Result<List<CitySearchDto>> {
        return try {
            Result.Success(citySearchApi.searchCity(searchTerm))
        } catch (e: Exception) {
            return Result.Error(e, ErrorType.RemoteError)
        }
    }
}