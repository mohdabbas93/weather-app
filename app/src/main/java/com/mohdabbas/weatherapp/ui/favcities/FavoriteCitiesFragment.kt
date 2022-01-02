package com.mohdabbas.weatherapp.ui.favcities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mohdabbas.weatherapp.R
import com.mohdabbas.weatherapp.data.source.local.FavoriteCity
import com.mohdabbas.weatherapp.persistence.PersistenceManager
import kotlinx.android.synthetic.main.fragment_favorite_cities.*

class FavoriteCitiesFragment : Fragment() {

    // TODO: Refactor this later
    private lateinit var persistenceManager: PersistenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        persistenceManager = PersistenceManager(requireContext())
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
        adapter = FavoriteCitiesAdapter(
            listOf(
                FavoriteCity(1, 24.5, 53.4, 34.0, "Sunny", "0d2", 13.0, 34, 1234.0),
                FavoriteCity(1, 24.5, 53.4, 34.0, "Sunny", "0d2", 13.0, 34, 1234.0),
                FavoriteCity(1, 24.5, 53.4, 34.0, "Sunny", "0d2", 13.0, 34, 1234.0),
                FavoriteCity(1, 24.5, 53.4, 34.0, "Sunny", "0d2", 13.0, 34, 1234.0),
                FavoriteCity(1, 24.5, 53.4, 34.0, "Sunny", "0d2", 13.0, 34, 1234.0)
            ),
            persistenceManager.isCelsius
        )
        favCitiesRecyclerView.adapter = adapter
    }
}