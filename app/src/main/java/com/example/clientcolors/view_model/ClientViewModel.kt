package com.example.clientcolors.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clientcolors.intents.ClientIntent
import com.example.clientcolors.model.Client

class ClientViewModel : ViewModel() {

    private val _clientState = MutableLiveData<Client>()
    val clientState: LiveData<Client> = _clientState

    fun handleIntent(intent: ClientIntent) {
        when (intent) {
            is ClientIntent.LoadClientData -> {
                _clientState.value = Client(intent.name, intent.color)
            }
        }
    }
}