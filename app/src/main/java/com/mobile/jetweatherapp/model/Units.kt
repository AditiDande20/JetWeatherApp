package com.mobile.jetweatherapp.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings_tbl")
data class Units(
    @ColumnInfo(name = "unit")
    @NonNull
    @PrimaryKey
    val unit : String

)
