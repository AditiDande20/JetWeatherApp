package com.mobile.jetweatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mobile.jetweatherapp.screens.about.AboutScreen
import com.mobile.jetweatherapp.screens.favorite.FavoriteScreen
import com.mobile.jetweatherapp.screens.main.MainScreen
import com.mobile.jetweatherapp.screens.main.MainViewModel
import com.mobile.jetweatherapp.screens.search.SearchScreen
import com.mobile.jetweatherapp.screens.setting.SettingScreen
import com.mobile.jetweatherapp.screens.splash.WeatherSplashScreen


@ExperimentalComposeUiApi
@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController)
        }
        composable(
            "${WeatherScreens.MainScreen.name}/{city}", arguments = listOf(
                navArgument(name = "city") {
                    type = NavType.StringType
                })
        ) { navBack ->
            navBack.arguments?.getString("city").let { city ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController, mainViewModel, city = city)
            }
        }
        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController)
        }
        composable(WeatherScreens.SettingScreen.name) {
            SettingScreen(navController)
        }
        composable(WeatherScreens.AboutScreen.name) {
            AboutScreen(navController)
        }
        composable(WeatherScreens.FavoriteScreen.name) {
            FavoriteScreen(navController)
        }
    }
}