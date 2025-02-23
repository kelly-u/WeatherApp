package com.weatherapp.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.LatLng
import com.weatherapp.ui.api.WeatherService
import com.weatherapp.ui.db.fb.FBDatabase.FBDatabase
import com.weatherapp.ui.model.City
import com.weatherapp.ui.model.User

class MainViewModel(private val db: FBDatabase, private val service : WeatherService) : ViewModel(),
    FBDatabase.Listener {
    private val _cities = mutableStateListOf<City>()
    val cities
        get() = _cities.toList()
    private val _user = mutableStateOf<User?>(null)
    val user: User?
        get() = _user.value

    init {
        db.setListener(this)
    }

    fun remove(city: City) {
        db.remove(city)
    }

    fun add(name: String) {
        service.getLocation(name) { lat, lng ->
            if (lat != null && lng != null) {
                db.add(City(name = name, location = LatLng(lat, lng)))
            }
        }
    }
    fun add(location: LatLng) {
        service.getName(location.latitude, location.longitude) { name ->
            if (name != null) {
                db.add(City(name = name, location = location))
            }
        }
    }

    override fun onUserLoaded(user: User) {
        _user.value = user
    }

    override fun onCityAdded(city: City) {
        _cities.add(city)
    }

    override fun onCityUpdated(city: City) {
        TODO("Not yet implemented")
    }

    override fun onCityRemoved(city: City) {
        _cities.remove(city)
    }
}

class MainViewModelFactory(private val db : FBDatabase,
                            private val service:WeatherService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(db, service) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
