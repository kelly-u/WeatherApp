package com.weatherapp.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

    sealed interface Route {
        @Serializable
        data object Home : Route
        @Serializable
        data object List : Route
        @Serializable
        data object Map : Route
    }
    sealed class BottomNavItem(
        var title: String,
        var icon: ImageVector,
        var route: Route)
    {
        data object HomeButton :
            BottomNavItem("Início", Icons.Default.Home, Route.Home)
        data object ListButton :
            BottomNavItem("Favoritos", Icons.Default.Favorite, Route.List)
        data object MapButton :
            BottomNavItem("Mapa", Icons.Default.LocationOn, Route.Map)
    }