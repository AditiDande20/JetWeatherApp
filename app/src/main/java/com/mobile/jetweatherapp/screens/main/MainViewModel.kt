package com.mobile.jetweatherapp.screens.main

import androidx.lifecycle.ViewModel
import com.mobile.jetweatherapp.data.DataOrException
import com.mobile.jetweatherapp.model.currentWeather.Weather
import com.mobile.jetweatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    suspend fun getCurrentWeatherData(
        city: String,
        unit: String
    ): DataOrException<Weather, Boolean, Exception> {
        return weatherRepository.getCurrentWeather(query = city, unit = unit)
    }

    suspend fun getWeatherForecastData(
        city: String,
        unit: String
    ): DataOrException<com.mobile.jetweatherapp.model.forecast.Weather, Boolean, Exception> {
        return weatherRepository.getWeatherForecast(city, unit = unit)
    }


}