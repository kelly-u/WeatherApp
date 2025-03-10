package com.weatherapp.ui.mainviewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weatherapp.ui.api.WeatherService
import com.weatherapp.ui.db.fb.FBDatabase.FBDatabase

class MainViewModelFactory(
    private val db: FBDatabase,
    private val service: WeatherService
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(db, service) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
