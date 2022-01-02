package com.mohdabbas.weatherapp.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mohdabbas.weatherapp.R
import com.mohdabbas.weatherapp.persistence.PersistenceManager
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    // Bad idea, will refactor later (Maybe use view model factory or DI)
    private lateinit var persistenceManager: PersistenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        persistenceManager = PersistenceManager(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRadioButtons()
    }

    private fun setupRadioButtons() {
        if (persistenceManager.isCelsius) {
            celsiusRadioButton.isChecked = true
        } else {
            fahrenheitRadioButton.isChecked = true
        }

        tempUnitRadioGroup.setOnCheckedChangeListener { _, i ->
            persistenceManager.isCelsius = celsiusRadioButton.id == i
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }
}