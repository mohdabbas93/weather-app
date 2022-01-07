package com.mohdabbas.weatherapp.ui.favcities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mohdabbas.weatherapp.R
import com.mohdabbas.weatherapp.ui.search.CitySearchActivity
import com.mohdabbas.weatherapp.util.ViewVisibilityUtil.makeGone
import com.mohdabbas.weatherapp.util.ViewVisibilityUtil.makeVisible
import com.mohdabbas.weatherapp.util.getViewModel
import kotlinx.android.synthetic.main.fragment_favorite_cities.*

class FavoriteCitiesFragment : Fragment() {

    private val viewModel by lazy { getViewModel(FavoriteCitiesViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupObservers()
    }

    override fun onResume() {
        super.onResume()

        viewModel.getFavoriteCities()
    }

    private fun setupObservers() {
        viewModel.loading.observe(this) {
            makeGone(loadingView, emptyView, favCitiesRecyclerView)
            if (it) {
                makeVisible(loadingView)
            }
        }
        viewModel.favoriteCities.observe(this) {
            if (it.isEmpty()) {
                makeVisible(emptyView)
            } else {
                makeVisible(favCitiesRecyclerView)
                adapter?.updateData(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_cities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOnClickListeners()
        setupFavoriteCitiesRecyclerView()
    }

    private fun setupOnClickListeners() {
        addCityButton.setOnClickListener {
            startActivity(Intent(context, CitySearchActivity::class.java))
        }
    }

    private var adapter: FavoriteCitiesAdapter? = null

    private fun setupFavoriteCitiesRecyclerView() {
        adapter = FavoriteCitiesAdapter(listOf())
        favCitiesRecyclerView.adapter = adapter
    }
}