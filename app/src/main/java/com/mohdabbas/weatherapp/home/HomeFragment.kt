package com.mohdabbas.weatherapp.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mohdabbas.weatherapp.R

class HomeFragment : Fragment() {

    private var fusedLocationClient: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = context
        if (context != null) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            getLastLocation(context)
        }

    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(context: Context) {
        if (checkPermissions(context)) {
            if (isLocationEnabled()) {
                Toast.makeText(context, "GPS is enabled", Toast.LENGTH_SHORT).show()
                fusedLocationClient?.lastLocation?.addOnSuccessListener { location ->
                    if (location == null) {
                        Toast.makeText(context, "Location null", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Location not null", Toast.LENGTH_SHORT).show()
                        Toast.makeText(
                            context,
                            "Lat: ${location.latitude}, Lng: ${location.longitude}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(context, "Please enable GPS", Toast.LENGTH_SHORT).show()
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

    private fun requestPermission() {
        val requestPermissionLauncher = registerForActivityResult(
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

        requestPermissionLauncher.launch(
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}