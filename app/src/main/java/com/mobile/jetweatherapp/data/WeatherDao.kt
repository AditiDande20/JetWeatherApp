package com.mobile.jetweatherapp.data

import androidx.room.*
import com.mobile.jetweatherapp.model.Favorite
import com.mobile.jetweatherapp.model.Units
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query(value = "select * from fav_tbl")
    fun getFavorites(): Flow<List<Favorite>>

    @Query(value = "select * from fav_tbl where city =:city")
    fun getFavoritesByID(city: String): Flow<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Query("delete from fav_tbl")
    suspend fun deleteAllFavorite()

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)


    //Unit
    @Query(value = "select * from settings_tbl")
    fun getUnits(): Flow<List<Units>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnits(units: Units)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnits(units: Units)

    @Query("delete from settings_tbl")
    suspend fun deleteAllUnits()

    @Delete
    suspend fun deleteUnits(units: Units)


}