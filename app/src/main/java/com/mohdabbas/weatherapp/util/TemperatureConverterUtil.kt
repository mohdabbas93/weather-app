package com.mohdabbas.weatherapp.util

/**
 * Created by Mohammad Abbas
 * On: 1/2/22.
 */
object TemperatureConverterUtil {

    fun Double.convertTemperature(isCelsius: Boolean): Double {
        return if (isCelsius) this else fromCelsiusToFahrenheit()
    }

    private fun Double.fromCelsiusToFahrenheit(): Double {
        return this * 9 /5 + 32
    }
}