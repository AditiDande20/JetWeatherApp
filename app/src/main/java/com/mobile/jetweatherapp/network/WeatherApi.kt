package com.mobile.jetweatherapp.network

import com.mobile.jetweatherapp.model.currentWeather.Weather
import com.mobile.jetweatherapp.utils.Constant
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {

    @GET(value = "data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("q") query: String,
        @Query("units") unit: String,
        @Query("appid") appid: String = Constant.API_KEY

    ): Weather

    @GET(value = "data/2.5/forecast")
    suspend fun getWeatherForecast(
        @Query("q") query: String,
        @Query("units") unit: String,
        @Query("appid") appid: String = Constant.API_KEY
    ): com.mobile.jetweatherapp.model.forecast.Weather


}