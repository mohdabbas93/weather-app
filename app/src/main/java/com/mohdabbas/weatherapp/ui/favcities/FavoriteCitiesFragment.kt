package com.mohdabbas.weatherapp.ui.favcities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mohdabbas.weatherapp.R
import com.mohdabbas.weatherapp.WeatherApplication
import com.mohdabbas.weatherapp.persistence.PersistenceManager
import kotlinx.android.synthetic.main.fragment_favorite_cities.*

class FavoriteCitiesFragment : Fragment() {

    // TODO: Refactor this later
    private lateinit var persistenceManager: PersistenceManager
    private val viewModel = FavoriteCitiesViewModel(WeatherApplication.WeatherRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        persistenceManager = PersistenceManager(requireContext())
        setupObservers()
        viewModel.getFavoriteCities()
    }

    private fun setupObservers() {
        viewModel.favoriteCities.observe(this) {
            adapter?.updateData(it)
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

        setupFavoriteCitiesRecyclerView()
    }

    private var adapter: FavoriteCitiesAdapter? = null

    private fun setupFavoriteCitiesRecyclerView() {
        adapter = FavoriteCitiesAdapter(listOf())
        favCitiesRecyclerView.adapter = adapter
    }
}