package com.mohdabbas.weatherapp.data.source.remote.citysearch

/**
 * Created by Mohammad Abbas
 * On: 1/5/22.
 */
data class CitySearchDto(
    val name: String,
    val country: CountryDto,
    val coordinates: CoordinatesDto
)

data class CountryDto(
    val name: String
)

data class CoordinatesDto(
    val latitude: Double,
    val longitude: Double
)