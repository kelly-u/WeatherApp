package com.weatherapp.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.weatherapp.ui.HomePage
import com.weatherapp.ui.ListPage
import com.weatherapp.MapPage
import com.weatherapp.ui.mainviewmodels.MainViewModel

@Composable
fun MainNavHost(navController: NavHostController, modifier: Modifier = Modifier, viewModel: MainViewModel) {
    NavHost(navController, startDestination = Route.Home) {
        composable<Route.Home> { HomePage(modifier, viewModel) }
        composable<Route.List> { ListPage(modifier, viewModel) }
        composable<Route.Map> { MapPage(modifier, viewModel) }
    }
}