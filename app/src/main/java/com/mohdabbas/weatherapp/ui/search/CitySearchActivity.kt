package com.mohdabbas.weatherapp.ui.search

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mohdabbas.weatherapp.R
import com.mohdabbas.weatherapp.WeatherApplication
import com.mohdabbas.weatherapp.data.Result
import com.mohdabbas.weatherapp.data.source.remote.citysearch.CitySearchDto
import com.mohdabbas.weatherapp.util.RecyclerViewUtil
import com.mohdabbas.weatherapp.util.RecyclerViewUtil.addSpacingDecorationForRecyclerView
import com.mohdabbas.weatherapp.util.ViewVisibilityUtil.makeGone
import com.mohdabbas.weatherapp.util.ViewVisibilityUtil.makeInvisible
import com.mohdabbas.weatherapp.util.ViewVisibilityUtil.makeVisible
import kotlinx.android.synthetic.main.activity_city_search.*

class CitySearchActivity : AppCompatActivity() {

    // TODO: Very stupid way to instantiate a view model, for it works for now (refactor later)
    private val viewModel = CitySearchViewModel(WeatherApplication.citySearchRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_search)

        setupClickListeners()
        setupCitySearchResultsAdapter()
        setupObservers()
    }

    private fun setupClickListeners() {
        searchButton.setOnClickListener {
            val searchTerm = citySearchInputEditText.text.toString().trim()
            if (searchTerm.isNotEmpty()) {
                viewModel.searchCity(searchTerm)
            }
        }
    }

    private var adapter: CitySearchResultsAdapter? = null

    private fun setupCitySearchResultsAdapter() {
        adapter = CitySearchResultsAdapter(listOf())
        citySearchResultsRecyclerView.adapter = adapter
        citySearchResultsRecyclerView.addItemDecoration(
            addSpacingDecorationForRecyclerView(
                RecyclerViewUtil.SpaceType.Vertical
            )
        )
    }

    private fun setupObservers() {
        viewModel.loading.observe(this) {
            makeGone(loadingView)
            makeInvisible(citySearchResultsRecyclerView)
            if (it) {
                makeVisible(loadingView)
            }
        }

        viewModel.searchResults.observe(this) {
            when (it) {
                is Result.Success -> {
                    makeVisible(citySearchResultsRecyclerView)
                    showCitySearchResults(it.data)
                }
                is Result.Error -> {
                    Toast.makeText(this, getString(R.string.failed_to_load), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun showCitySearchResults(data: List<CitySearchDto>) {
        adapter?.updateData(data)
    }
}