package com.weatherapp.ui

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.weatherapp.ui.model.City


class MainViewModel : ViewModel() {
    private val _cities = getCities().toMutableStateList()
    val cities
        get() = _cities.toList()
    fun remove(city: City) {
        _cities.remove(city)
    }
    fun add(name: String) {
        _cities.add(City(name = name))
    }
}

private fun getCities() = List(20) { i ->
    City(name = "Cidade $i", weather = "Carregando clima...")
}