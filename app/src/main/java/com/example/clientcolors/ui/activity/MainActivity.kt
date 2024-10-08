package com.example.clientcolors.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.clientcolors.R
import com.example.clientcolors.intents.ClientIntent
import com.example.clientcolors.ui.theme.ClientColorsTheme
import com.example.clientcolors.utils.Location
import com.example.clientcolors.utils.Location.startLocationUpdates
import com.example.clientcolors.view_model.ClientViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: ClientViewModel by viewModels()

    private val requestLocationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            startLocationUpdates(viewModel)
        }
    }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel.handleIntent(
            ClientIntent.LoadClientData(
                getString(R.string.client_name),
                getString(R.color.client_color)
            )
        )
        Location.locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            startLocationUpdates(viewModel)
        }

        setContent {
            ClientColorsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { PaddingValues ->
                    val clientState by viewModel.clientState.observeAsState()
                    val clientCoordinatesState by viewModel.clientCoordinateState.observeAsState()

                    Greeting(
                        clientState!!.name,
                        clientState!!.color,
                        clientCoordinatesState?.latitude ?: "",
                        clientCoordinatesState?.longitude ?: "",
                        Modifier.padding(PaddingValues)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    color: String,
    latitude: String,
    longitude: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$name - $color",
            modifier = modifier
        )
        Text(text = "Latitude: $latitude")
        Text(text = "Longitude: $longitude")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ClientColorsTheme {
        Greeting("OperatorA", "#A4564", "55.67", "`103.24")
    }
}