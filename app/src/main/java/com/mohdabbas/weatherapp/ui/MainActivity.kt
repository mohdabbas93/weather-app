package com.mohdabbas.weatherapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mohdabbas.weatherapp.R
import com.mohdabbas.weatherapp.ui.favcities.FavoriteCitiesFragment
import com.mohdabbas.weatherapp.ui.home.HomeFragment
import com.mohdabbas.weatherapp.ui.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        val homeFragment = HomeFragment()
        val favCitiesFragment = FavoriteCitiesFragment()
        val settingsFragment = SettingsFragment()

        setCurrentFragment(homeFragment)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> setCurrentFragment(homeFragment)
                R.id.fav_cities -> setCurrentFragment(favCitiesFragment)
                R.id.settings -> setCurrentFragment(settingsFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, fragment)
            commit()
        }
}