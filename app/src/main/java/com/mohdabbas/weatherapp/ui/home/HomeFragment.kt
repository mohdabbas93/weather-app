package com.mohdabbas.weatherapp.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.view.*
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.mohdabbas.weatherapp.R
import com.mohdabbas.weatherapp.WeatherApplication
import com.mohdabbas.weatherapp.WeatherApplication.Companion.persistenceManager
import com.mohdabbas.weatherapp.data.Result
import com.mohdabbas.weatherapp.data.source.remote.dto.CityWeatherDto
import com.mohdabbas.weatherapp.ui.search.CitySearchActivity
import com.mohdabbas.weatherapp.util.ErrorType
import com.mohdabbas.weatherapp.util.RecyclerViewUtil
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

    private val resolutionForResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                getLastLocation(requireContext())
            } else {
                Toast.makeText(context, getString(R.string.enable_location), Toast.LENGTH_SHORT)
                    .show()
                viewModel.setLocationUnavailable()
            }
        }

    enum class Path { Main, Search, Favorite }

    private var path = Path.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupObservers()

        val id = arguments?.getInt("id")
        val lat = arguments?.getDouble("lat", 0.0)
        val lng = arguments?.getDouble("lng", 0.0)

        when {
            id != 0 && id != null && lat != null && lng != null -> {
                path = Path.Favorite
                viewModel.cityWeatherId = id
                favoritePath(id)
            }
            id == 0 && lat != null && lng != null -> {
                path = Path.Search
                searchPath(lat, lng)
            }
            else -> {
                path = Path.Main
                mainPath()
            }
        }
    }

    private fun mainPath() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        createLocationRequest()
        viewModel.getCurrentLocationWeatherData()
    }

    private fun searchPath(lat: Double, lng: Double) {
        viewModel.getWeatherData(lat, lng, storeInDb = false)
    }

    private fun favoritePath(cityWeatherId: Int) {
        viewModel.getFavoriteCityWeatherData(cityWeatherId)
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest.create()
        locationRequest?.interval = 4000
        locationRequest?.fastestInterval = 2000
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun setupObservers() {
        viewModel.locationUnavailable.observe(this) {
            makeGone(loadingView)
            makeVisible(noSavedDataView)
        }

        viewModel.loading.observe(this) {
            makeGone(loadingView, mainView, errorView, noSavedDataView)
            if (it) {
                makeVisible(loadingView)
            }
        }

        viewModel.localWeatherData.observe(this) {
            when (it) {
                is Result.Success -> {
                    makeVisible(mainView)
                    showWeatherData(it.data)
                    getLastLocation(requireContext())
                }
                is Result.Error -> {
                    makeVisible(noSavedDataView)
                }
            }
        }

        viewModel.weatherData.observe(this) {
            when (it) {
                is Result.Success -> {
                    makeVisible(mainView)
                    showWeatherData(it.data)
                }
                is Result.Error -> {
                    when {
                        it.errorType == ErrorType.NoSavedData -> {
                            makeVisible(noSavedDataView)
                        }
                        it.errorType == ErrorType.RemoteError &&
                                viewModel.localWeatherData.value is Result.Error -> {
                            makeVisible(errorView)
                        }
                        else -> {
                            makeVisible(mainView)
                        }
                    }
                }
            }
        }

        viewModel.localFavoriteCityWeatherData.observe(this) {
            when (it) {
                is Result.Success -> {
                    makeVisible(mainView)
                    showWeatherData(it.data)

                    viewModel.getWeatherData(
                        it.data.lat,
                        it.data.lng,
                        hasLoading = false,
                        isDefault = false
                    )
                }
                is Result.Error -> {
                    if (it.errorType == ErrorType.NoSavedData) {
                        makeVisible(noSavedDataView)
                    }
                }
            }
        }
    }

    private fun showWeatherData(weatherData: CityWeatherDto) {

        locationNameTextView.text = getLocationName(weatherData.lat, weatherData.lng)
        dateAndTimeTextView.text =
            SimpleDateFormat("EE d MMMM").format(Date())
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
            return addresses[0].locality ?: addresses[0].subLocality ?: addresses[0].adminArea
            ?: addresses[0].subAdminArea ?: getString(R.string.no_city_name)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return getString(R.string.no_city_name)
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(context: Context) {
        if (checkPermissions(context)) {
            if (isLocationEnabled()) {
                fusedLocationClient?.lastLocation?.addOnSuccessListener { location ->
                    if (location == null) {
                        startLocationUpdates()
                    } else {
                        viewModel.getWeatherData(
                            location.latitude,
                            location.longitude,
                            hasLoading = false
                        )
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
            viewModel.setLocationUnavailable()
            Toast.makeText(
                context,
                getString(R.string.location_permission_denied),
                Toast.LENGTH_SHORT
            )
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

        (activity as AppCompatActivity).setSupportActionBar(homeToolbar)
        setHasOptionsMenu(true)

        setupOnClickListeners()
        setupDailyRecyclerView()
    }

    private fun navigateCitySearchActivity() {
        startActivity(Intent(context, CitySearchActivity::class.java))
    }

    private fun setupOnClickListeners() {
        addCurrentLocationButton.setOnClickListener {
            viewModel.setLoading()
            getLastLocation(requireContext())
        }
        retryButton.setOnClickListener {
            viewModel.setLoading()
            getLastLocation(requireContext())
        }
    }

    private var adapter: DailyWeatherAdapter? = null

    private fun setupDailyRecyclerView() {
        adapter = DailyWeatherAdapter(listOf(), persistenceManager.isCelsius)
        dailyRecyclerView.adapter = adapter
        dailyRecyclerView.addItemDecoration(
            RecyclerViewUtil.addSpacingDecorationForRecyclerView(
                RecyclerViewUtil.SpaceType.Horizontal
            )
        )
    }

    private var optionItemsMenu: Menu? = null

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        optionItemsMenu = menu
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.search).isVisible = path == Path.Main
        menu.findItem(R.id.favorite).apply {
            isVisible = path != Path.Main
            updateFavoriteIcon(path == Path.Favorite)
        }
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> navigateCitySearchActivity()
            R.id.favorite -> {
                if (path == Path.Search) {
                    updateFavoriteIcon(true)
                    viewModel.addFavoriteCity()
                } else if (path == Path.Favorite) {
                    updateFavoriteIcon(false)
                    viewModel.deleteFavoriteCity()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        optionItemsMenu?.findItem(R.id.favorite)?.apply {
            val iconRes =
                if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_outlined
            icon = ContextCompat.getDrawable(requireContext(), iconRes)
        }
    }
}