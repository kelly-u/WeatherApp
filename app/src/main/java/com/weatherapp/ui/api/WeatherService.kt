package com.weatherapp.ui.api

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherService {
    private var weatherAPI: WeatherServiceAPI
    init {
        val retrofitAPI = Retrofit.Builder().baseUrl(WeatherServiceAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
        weatherAPI = retrofitAPI.create(WeatherServiceAPI::class.java)
    }
    fun getName(lat: Double, lng: Double, onResponse : (String?) -> Unit ) {
        search("$lat,$lng") { loc -> onResponse (loc?.name) }
    }
    fun getLocation(name: String, onResponse: (lat:Double?, long:Double?) -> Unit) {
        search(name) { loc -> onResponse (loc?.lat, loc?.lon) }
    }
    private fun search(query: String, onResponse : (APILocation?) -> Unit) {
        val call: Call<List<APILocation>?> = weatherAPI.search(query)
        call.enqueue(object : Callback<List<APILocation>?> {
            override fun onResponse(call: Call<List<APILocation>?>,
                                    response: Response<List<APILocation>?>
            ) {
                onResponse(response.body()?.let {if (it.isNotEmpty()) it[0] else null})
            }
            override fun onFailure(call: Call<List<APILocation>?>,t: Throwable) {
                Log.w("WeatherApp WARNING", "" + t.message)
                onResponse(null)
            }
        })
    }
}