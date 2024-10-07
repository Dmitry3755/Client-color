package com.example.clientcolors

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.clientcolors.intents.ClientIntent
import com.example.clientcolors.ui.theme.ClientColorsTheme
import com.example.clientcolors.view_model.ClientViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: ClientViewModel by viewModels()

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel.handleIntent(ClientIntent.LoadClientData(getString(R.string.client_name), getString(R.color.client_color)))

        setContent {
            ClientColorsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { PaddingValues ->
                    Greeting(
                        viewModel.clientState.value!!.name,
                        viewModel.clientState.value!!.color,
                        Modifier.padding(PaddingValues)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, color: String, modifier: Modifier = Modifier) {
    Text(
        text = "$name - $color",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ClientColorsTheme {
        Greeting("OperatorA", "#A4564")
    }
}