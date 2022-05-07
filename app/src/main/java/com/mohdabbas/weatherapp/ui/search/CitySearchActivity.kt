package com.mohdabbas.weatherapp.ui.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mohdabbas.weatherapp.R
import com.mohdabbas.weatherapp.data.Result
import com.mohdabbas.weatherapp.data.source.remote.citysearch.CitySearchDto
import com.mohdabbas.weatherapp.util.RecyclerViewUtil
import com.mohdabbas.weatherapp.util.RecyclerViewUtil.addSpacingDecorationForRecyclerView
import com.mohdabbas.weatherapp.util.ViewVisibilityUtil.makeGone
import com.mohdabbas.weatherapp.util.ViewVisibilityUtil.makeInvisible
import com.mohdabbas.weatherapp.util.ViewVisibilityUtil.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_city_search.*

@AndroidEntryPoint
class CitySearchActivity : AppCompatActivity() {

    private val viewModel: CitySearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_search)

        setupSearchField()
        setupCitySearchResultsAdapter()
        setupObservers()
    }

    private fun setupSearchField() {
        citySearchInputEditText.setOnEditorActionListener(OnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchTerm = textView.text.toString().trim()
                if (searchTerm.isNotEmpty()) {
                    viewModel.searchCity(searchTerm)
                }
                return@OnEditorActionListener true
            }
            false
        })
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