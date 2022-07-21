package com.mobile.jetweatherapp.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mobile.jetweatherapp.data.DataOrException
import com.mobile.jetweatherapp.model.Units
import com.mobile.jetweatherapp.model.currentWeather.Weather
import com.mobile.jetweatherapp.navigation.WeatherScreens
import com.mobile.jetweatherapp.screens.setting.SettingViewModel
import com.mobile.jetweatherapp.utils.formatDate
import com.mobile.jetweatherapp.utils.formatDecimals
import com.mobile.jetweatherapp.widgets.*
import java.util.*

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingViewModel: SettingViewModel = hiltViewModel(),
    city: String?
) {
    val curCity = if (city!!.isBlank()) "Seattle" else city
    var unitFromDB = settingViewModel.unitList.collectAsState().value

    if (unitFromDB.isEmpty()) {
        unitFromDB = listOf(Units("metric"))
    }

    var units by remember {
        mutableStateOf("imperial")
    }
    var isImperial by remember {
        mutableStateOf(false)
    }

    if (unitFromDB.isNotEmpty()) {
        units = unitFromDB[0].unit.split(" ")[0].lowercase(Locale.ROOT)
        isImperial = units == "imperial"
        val currentWeatherData = produceState(
            initialValue = DataOrException(loading = true)
        ) {
            value = city.let { mainViewModel.getCurrentWeatherData(city = curCity, unit = units) }
        }.value

        val weatherForecastData =
            produceState(
                initialValue = DataOrException(loading = true)
            ) {
                value =
                    city.let { mainViewModel.getWeatherForecastData(city = curCity, unit = units) }
            }.value

        if (currentWeatherData.loading == true) {
            CircularProgressIndicator()
        } else if (currentWeatherData.data != null) {
            MainScreenScaffold(
                currentWeatherData.data!!,
                weatherForecastData.data,
                navController,
                isImperial = isImperial
            )
        }
    }


}

@Composable
fun MainScreenScaffold(
    currentWeather: Weather,
    weatherForecast: com.mobile.jetweatherapp.model.forecast.Weather?,
    navController: NavController,
    isImperial: Boolean
) {
    Scaffold(topBar = {
        WeatherTopAppBar(
            title = "${currentWeather.name},${currentWeather.sys.country}",
            elevation = 5.dp,
            navController = navController,
            onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            }
        )
    }) {
        MainContent(currentWeather, weatherForecast, isImperial = isImperial)
    }

}

@Composable
fun MainContent(
    currentWeather: Weather,
    weatherForecast: com.mobile.jetweatherapp.model.forecast.Weather?,
    isImperial: Boolean
) {
    val imageUrl =
        "https://openweathermap.org/img/wn/${currentWeather.weather.first().icon}.png"

    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatDate(currentWeather.dt),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = Modifier.padding(6.dp)
        )

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(160.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                WeatherStateImage(imageUrl = imageUrl)
                Text(
                    text = formatDecimals(currentWeather.main.temp) + "°",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(text = currentWeather.weather.first().main, fontStyle = FontStyle.Italic)
            }
        }
        WeatherHumidityPressureRow(weather = currentWeather, isImperial = isImperial)
        Divider()
        SunriseAndSunsetRow(weather = currentWeather)
        Text(
            text = "This Week",
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold
        )
        WeatherForecast(weatherForecast = weatherForecast)
    }
}

