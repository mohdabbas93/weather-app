package com.mohdabbas.weatherapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohdabbas.weatherapp.data.Result
import com.mohdabbas.weatherapp.data.source.CitySearchRepository
import com.mohdabbas.weatherapp.data.source.remote.citysearch.CitySearchDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Mohammad Abbas
 * On: 1/5/22.
 */
class CitySearchViewModel(
    private val citySearchRepository: CitySearchRepository
) : ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _searchResults = MutableLiveData<Result<List<CitySearchDto>>>()
    val searchResults: LiveData<Result<List<CitySearchDto>>> = _searchResults

    fun searchCity(searchTerm: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            val response = citySearchRepository.searchCity(searchTerm)
            _loading.postValue(false)
            _searchResults.postValue(response)
        }
    }
}