package com.example.clientcolors.intents

sealed class ClientIntent {
    data class LoadClientData(val name: String, val color: String) : ClientIntent()
}