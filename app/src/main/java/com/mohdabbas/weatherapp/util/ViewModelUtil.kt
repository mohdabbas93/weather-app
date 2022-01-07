package com.mohdabbas.weatherapp.util

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by Mohammad Abbas
 * On: 1/7/22.
 */

fun <T : ViewModel> AppCompatActivity.getViewModel(viewModelClass: Class<T>) =
    ViewModelProvider(this, ViewModelFactory.getInstance())[viewModelClass]