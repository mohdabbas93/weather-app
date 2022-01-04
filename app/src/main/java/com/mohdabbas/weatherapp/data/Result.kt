package com.mohdabbas.weatherapp.data

import com.mohdabbas.weatherapp.util.ErrorType

/**
 * Created by Mohammad Abbas
 * On: 1/4/22.
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception, val errorType: ErrorType) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
