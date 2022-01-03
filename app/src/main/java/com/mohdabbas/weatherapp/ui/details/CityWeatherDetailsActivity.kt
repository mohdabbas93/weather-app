package com.mohdabbas.weatherapp.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mohdabbas.weatherapp.R
import com.mohdabbas.weatherapp.ui.home.HomeFragment

class CityWeatherDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_weather_details)

        val lat = intent.getDoubleExtra("lat", 0.0)
        val lng = intent.getDoubleExtra("lng", 0.0)

        val homeFragment = HomeFragment().apply {
            arguments = Bundle().apply {
                putDouble("lat", lat)
                putDouble("lng", lng)
            }
        }

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, homeFragment)
            commit()
        }
    }
}