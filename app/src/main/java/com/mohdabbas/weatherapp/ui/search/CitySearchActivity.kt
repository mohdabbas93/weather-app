package com.mohdabbas.weatherapp.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mohdabbas.weatherapp.R
import com.mohdabbas.weatherapp.WeatherApplication
import kotlinx.android.synthetic.main.activity_city_search.*

class CitySearchActivity : AppCompatActivity() {

    // TODO: Very stupid way to instantiate a view model, for it works for now (refactor later)
    private val viewModel = CitySearchViewModel(WeatherApplication.citySearchRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_search)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        searchButton.setOnClickListener {
            val searchTerm = citySearchInputEditText.text.toString().trim()
            if (searchTerm.isNotEmpty()) {
                viewModel.searchCity(searchTerm)
            }
        }
    }
}