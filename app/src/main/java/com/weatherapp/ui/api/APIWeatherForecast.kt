package com.weatherapp.ui.api

import com.weatherapp.ui.api.WeatherServiceAPI.Companion.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


data class APIWeatherForecast(

    var location: APILocation? = null,
    var current: APIWeatherForecast? = null,
    var forecast: APIForecast? = null,

)

