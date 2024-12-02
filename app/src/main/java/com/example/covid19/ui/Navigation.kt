package com.example.covid19.ui

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.covid19.ui.screens.principal.HomeScreen
import com.example.covid19.ui.screens.principal.StartScreen
import com.example.covid19.ui.screens.country.CountryScreen


sealed class Routes(val route: String) {
    //Path 1
    data object HomeScreen : Routes("homeScreen")

    data object StartScreen : Routes("startScreen")

    data object  ContinentScreen : Routes("continentScreen")

    data object CountryScreen : Routes("countryScreen")

    data object detCountryScreen : Routes("detCountryScreen")

}

@Composable
fun AppNavigation(modifier: Modifier = Modifier){
    val navHostController : NavHostController = rememberNavController()

    NavHost(navController = navHostController, startDestination = Routes.StartScreen.route,
        modifier = modifier){
        composable(Routes.StartScreen.route){
            StartScreen(navHostController = navHostController)
        }
        composable(Routes.HomeScreen.route){
            HomeScreen(navHostController = navHostController)
        }
        composable(Routes.CountryScreen.route){
            CountryScreen(navHostController = navHostController)
        }
        // Path to details screen
    }
}