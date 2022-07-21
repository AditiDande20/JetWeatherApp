package com.mobile.jetweatherapp.repository

import android.util.Log
import com.mobile.jetweatherapp.data.DataOrException
import com.mobile.jetweatherapp.model.currentWeather.Weather
import com.mobile.jetweatherapp.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi) {

    suspend fun getCurrentWeather(
        query: String,
        unit: String
    ): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            weatherApi.getCurrentWeather(query = query, unit = unit)
        } catch (e: Exception) {
            Log.e("Aditi===>", "Exception i unit :: " + e.message)
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }

    suspend fun getWeatherForecast(
        query: String,
        unit: String
    ): DataOrException<com.mobile.jetweatherapp.model.forecast.Weather, Boolean, Exception> {
        val response = try {
            weatherApi.getWeatherForecast(query, unit = unit)
        } catch (e: Exception) {
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }


}