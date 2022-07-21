package com.mobile.jetweatherapp.di

import android.content.Context
import androidx.room.Room
import com.mobile.jetweatherapp.data.WeatherDao
import com.mobile.jetweatherapp.data.WeatherDatabase
import com.mobile.jetweatherapp.network.WeatherApi
import com.mobile.jetweatherapp.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao {
        return weatherDatabase.getFavoriteDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): WeatherDatabase {
        return Room.databaseBuilder(
            context, WeatherDatabase::class.java, "weather_db"
        ).fallbackToDestructiveMigration().build()
    }

}