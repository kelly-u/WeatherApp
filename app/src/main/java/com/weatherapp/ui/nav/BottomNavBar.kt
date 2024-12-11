package com.weatherapp.ui.nav

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(navController: NavHostController, items : List<BottomNavItem>) {
    NavigationBar(
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination
        items.forEach { item ->
            NavigationBarItem (
                icon = { Icon(imageVector = item.icon, contentDescription= item.title)},
                label = { Text(text = item.title, fontSize = 12.sp) },
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Volta pilha de navegação até HomePage (startDest).
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                            restoreState = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
