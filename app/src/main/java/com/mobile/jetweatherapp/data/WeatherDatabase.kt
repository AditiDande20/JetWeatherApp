package com.mobile.jetweatherapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mobile.jetweatherapp.model.Favorite
import com.mobile.jetweatherapp.model.Units

@Database(entities = [Favorite::class, Units::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun getFavoriteDao(): WeatherDao

}