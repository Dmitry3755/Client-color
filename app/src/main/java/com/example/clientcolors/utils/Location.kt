package com.example.clientcolors.utils

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import com.example.clientcolors.intents.ClientIntent
import com.example.clientcolors.view_model.ClientViewModel

object Location {
    lateinit var locationManager: LocationManager

    @SuppressLint("MissingPermission")
    fun startLocationUpdates(viewModel: ClientViewModel) {
        val locationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                viewModel.handleIntent(
                    ClientIntent.LoadLocation(
                        location.latitude.toString(),
                        location.longitude.toString()
                    )
                )
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0L,
            0f,
            locationListener
        )
    }
}