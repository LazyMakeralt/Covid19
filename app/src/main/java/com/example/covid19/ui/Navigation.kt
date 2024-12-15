package com.example.covid19.ui

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.covid19.ui.screens.continent.ContinentScreen
import com.example.covid19.ui.screens.principal.HomeScreen
import com.example.covid19.ui.screens.principal.StartScreen
import com.example.covid19.ui.screens.country.CountryScreen

import com.example.covid19.ui.screens.goodPrac.GoodPracticesScreen
import com.example.covid19.ui.screens.quiz.QuizzScreen

import com.example.covid19.ui.screens.worldwide.WorldwideStatScreen


sealed class Routes(val route: String) {
    //Path 1
    data object HomeScreen : Routes("homeScreen")

    data object StartScreen : Routes("startScreen")

    data object  ContinentScreen : Routes("continentScreen")

    data object  ContinentScreenE : Routes("continentScreenE")

    data object CountryScreen : Routes("countryScreen")

    data object QuizzScreen : Routes("quizzScreen")

    data object GoodPracticesScreen : Routes("goodPractices")


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

        composable(Routes.ContinentScreenE.route){
            ContinentScreen(navHostController = navHostController)
        }
        composable(Routes.ContinentScreen.route){
            WorldwideStatScreen(navHostController = navHostController)
        }
        composable(Routes.GoodPracticesScreen.route){
            GoodPracticesScreen(navHostController = navHostController)
        }
        composable(Routes.QuizzScreen.route){
            QuizzScreen(navHostController = navHostController)
        }

    }
}