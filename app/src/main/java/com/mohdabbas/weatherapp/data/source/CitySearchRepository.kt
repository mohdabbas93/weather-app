package com.mohdabbas.weatherapp.data.source

import com.mohdabbas.weatherapp.data.Result
import com.mohdabbas.weatherapp.data.source.remote.citysearch.CitySearchDto
import com.mohdabbas.weatherapp.data.source.remote.citysearch.CitySearchRemoteDataSource
import javax.inject.Inject

/**
 * Created by Mohammad Abbas
 * On: 1/5/22.
 */
class CitySearchRepository @Inject constructor(
    private val remoteDataSource: CitySearchRemoteDataSource
) : CitySearchDataSource {
    override suspend fun searchCity(searchTerm: String): Result<List<CitySearchDto>> {
        return remoteDataSource.searchCity(searchTerm)
    }
}