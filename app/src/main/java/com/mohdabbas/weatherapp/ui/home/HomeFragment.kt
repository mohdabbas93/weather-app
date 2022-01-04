package com.mohdabbas.weatherapp.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Rect
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.mohdabbas.weatherapp.R
import com.mohdabbas.weatherapp.WeatherApplication
import com.mohdabbas.weatherapp.data.Result
import com.mohdabbas.weatherapp.data.source.remote.dto.CityWeatherDto
import com.mohdabbas.weatherapp.persistence.PersistenceManager
import com.mohdabbas.weatherapp.util.ErrorType
import com.mohdabbas.weatherapp.util.TemperatureConverterUtil.convertTemperature
import com.mohdabbas.weatherapp.util.ViewVisibilityUtil.makeGone
import com.mohdabbas.weatherapp.util.ViewVisibilityUtil.makeVisible
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null

    // TODO: Very stupid way to instantiate a view model, for it works for now
    private val viewModel = HomeViewModel(WeatherApplication.WeatherRepository)

    // TODO: Refactor this later
    private lateinit var persistenceManager: PersistenceManager

    private val resolutionForResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK)
                getLastLocation(requireContext())
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupObservers()
        viewModel.getCurrentLocationWeatherData()

        persistenceManager = PersistenceManager((requireContext()))

        // First get data if not exist as to get location and not refresh data
        // if there is data then refresh

        val lat = arguments?.getDouble("lat", 0.0)
        val lng = arguments?.getDouble("lng", 0.0)

        if (lat != null && lng != null) {
            viewModel.getWeatherData(lat, lng)
        } else {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
            createLocationRequest()
            // getLastLocation(requireContext())
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest.create()
        locationRequest?.interval = 4000
        locationRequest?.fastestInterval = 2000
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun setupObservers() {
        viewModel.loading.observe(this) {
            makeGone(loadingView, mainView, errorView, noSavedDataView)
            if (it) {
                makeVisible(loadingView)
            }
        }

        viewModel.weatherData.observe(this) {
            when (it) {
                is Result.Success -> {
                    makeVisible(mainView)
                    showWeatherData(it.data)
                }
                is Result.Error -> {
                    if (it.errorType == ErrorType.NoSavedData) {
                        makeVisible(noSavedDataView)
                    } else {
                        makeVisible(errorView)
                    }
                }
            }
        }
    }

    private fun showWeatherData(weatherData: CityWeatherDto) {

        locationNameTextView.text = getLocationName(weatherData.lat, weatherData.lng)
        dateAndTimeTextView.text =
            SimpleDateFormat("EE d MMMM HH:mm a").format(weatherData.currentWeather.currentUTCTime * 1000)
        currentTempTextView.text =
            getString(
                R.string.current_temp,
                weatherData.currentWeather.temperature.convertTemperature(
                    persistenceManager.isCelsius
                )
                    .toInt()
            )
        weatherConditionTextView.text =
            weatherData.currentWeather.weather.firstOrNull()?.weatherCondition ?: ""
        minAndMaxTempText.text =
            getString(
                R.string.feels_like_temp,
                weatherData.currentWeather.feelsLike.convertTemperature(
                    persistenceManager.isCelsius
                )
                    .toInt()
            )

        Glide.with(this)
            .load("http://openweathermap.org/img/wn/${weatherData.currentWeather.weather.firstOrNull()?.icon}@2x.png")
            .centerCrop()
            .into(weatherConditionIcon)
        adapter?.updateData(weatherData.dailyWeather)

        windSpeedTextView.text =
            getString(
                R.string.wind_speed_template,
                weatherData.currentWeather.windSpeed.toInt()
            )

        humiditySpeedTextView.text =
            getString(R.string.humidity_template, weatherData.currentWeather.humidity)

        pressureSpeedTextView.text =
            getString(
                R.string.pressure_template,
                weatherData.currentWeather.pressure.toInt()
            )
    }

    private fun getLocationName(lat: Double, lng: Double): String {
        try {
            val addresses = Geocoder(context, Locale.getDefault()).getFromLocation(lat, lng, 1)
            return addresses[0].adminArea
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return ""
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(context: Context) {
        if (checkPermissions(context)) {
            if (isLocationEnabled()) {
                fusedLocationClient?.lastLocation?.addOnSuccessListener { location ->
                    if (location == null) {
                        startLocationUpdates()
                    } else {
                        viewModel.getWeatherData(location.latitude, location.longitude)
                    }
                }
            } else {
                checkLocationSettings()
            }
        } else {
            requestPermission()
        }
    }

    private fun checkPermissions(context: Context) = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun isLocationEnabled(): Boolean {
        val locationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
                ?: return false
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun checkLocationSettings() {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest!!)
            .setAlwaysShow(true)

        val result = LocationServices.getSettingsClient(requireContext())
            .checkLocationSettings(builder.build())

        result.addOnCompleteListener { task ->
            try {
                val response = task.getResult(ApiException::class.java)
            } catch (e: ApiException) {
                if (e.statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                    askUserToOpenLocation(e as ResolvableApiException)
                }
            }
        }
    }

    private fun askUserToOpenLocation(resolvableApiException: ResolvableApiException) {
        val intentSenderRequest =
            IntentSenderRequest.Builder(resolvableApiException.resolution)
                .build()
        resolutionForResultLauncher.launch(intentSenderRequest)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            val context = context
            if (context != null) getLastLocation(context)
        } else {
            Toast.makeText(context, "Location permission not granted", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun requestPermission() {
        requestPermissionLauncher.launch(
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        locationRequest?.let { locationRequest ->
            fusedLocationClient?.requestLocationUpdates(
                locationRequest,
                locationCallback(),
                Looper.getMainLooper()
            )
        }
    }

    private fun locationCallback() = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            stopLocationUpdate()

            viewModel.getWeatherData(
                locationResult.lastLocation.latitude,
                locationResult.lastLocation.longitude
            )
        }
    }

    private fun stopLocationUpdate() {
        fusedLocationClient?.removeLocationUpdates(locationCallback())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOnClickListeners()
        setupDailyRecyclerView()
    }

    private fun setupOnClickListeners() {
        add_current_location_button.setOnClickListener {
            viewModel.setLoading()
            getLastLocation(requireContext())
        }
    }

    private var adapter: DailyWeatherAdapter? = null

    private fun setupDailyRecyclerView() {
        adapter = DailyWeatherAdapter(listOf(), persistenceManager.isCelsius)
        dailyRecyclerView.adapter = adapter

        addDecorationForRecyclerView()
    }

    private fun addDecorationForRecyclerView() {
        val itemDecoration = object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.left = 15
                outRect.right = 15
            }
        }

        dailyRecyclerView.addItemDecoration(itemDecoration)
    }
}