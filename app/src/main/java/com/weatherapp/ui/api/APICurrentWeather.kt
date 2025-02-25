package com.weatherapp.ui.api

data class APICurrentWeather(
    var location: APILocation? = null,
    var current: APIWeather? = null
)
