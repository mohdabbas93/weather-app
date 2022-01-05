package com.mohdabbas.weatherapp.data.source

import com.mohdabbas.weatherapp.data.Result
import com.mohdabbas.weatherapp.data.source.remote.citysearch.CitySearchDto

/**
 * Created by Mohammad Abbas
 * On: 1/5/22.
 */
interface CitySearchDataSource {
    suspend fun searchCity(searchTerm: String): Result<List<CitySearchDto>>
}