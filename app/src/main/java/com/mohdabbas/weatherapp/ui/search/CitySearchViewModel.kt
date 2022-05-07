package com.mohdabbas.weatherapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohdabbas.weatherapp.data.Result
import com.mohdabbas.weatherapp.data.source.CitySearchRepository
import com.mohdabbas.weatherapp.data.source.remote.citysearch.CitySearchDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Mohammad Abbas
 * On: 1/5/22.
 */
@HiltViewModel
class CitySearchViewModel @Inject constructor(
    private val citySearchRepository: CitySearchRepository
) : ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _searchResults = MutableLiveData<Result<List<CitySearchDto>>>()
    val searchResults: LiveData<Result<List<CitySearchDto>>> = _searchResults

    private var job: Job? = null

    fun searchCity(searchTerm: String) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            val response = citySearchRepository.searchCity(searchTerm)
            _loading.postValue(false)
            _searchResults.postValue(response)
        }
    }
}