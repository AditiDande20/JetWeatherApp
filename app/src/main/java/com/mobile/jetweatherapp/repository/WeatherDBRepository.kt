package com.mobile.jetweatherapp.repository

import com.mobile.jetweatherapp.data.WeatherDao
import com.mobile.jetweatherapp.model.Favorite
import com.mobile.jetweatherapp.model.Units
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDBRepository @Inject constructor(private val weatherDao: WeatherDao) {
    fun getFavorites(): Flow<List<Favorite>> {
        return weatherDao.getFavorites()
    }

    suspend fun insertFavorite(favorite: Favorite) {
        return weatherDao.insertFavorite(favorite)
    }

    suspend fun updateFavorite(favorite: Favorite) {
        return weatherDao.updateFavorite(favorite)
    }

    suspend fun deleteFavorite(favorite: Favorite) {
        return weatherDao.deleteFavorite(favorite)
    }

    //Units
    fun getUnits(): Flow<List<Units>> {
        return weatherDao.getUnits()
    }

    suspend fun insertUnits(units: Units) {
        return weatherDao.insertUnits(units)
    }

    suspend fun updateUnits(units: Units) {
        return weatherDao.updateUnits(units)
    }

    suspend fun deleteUnits(units: Units) {
        return weatherDao.deleteUnits(units)
    }

    suspend fun deleteAllUnits() {
        return weatherDao.deleteAllUnits()
    }


}