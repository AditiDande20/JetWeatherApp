package com.mobile.jetweatherapp.screens.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.jetweatherapp.model.Units
import com.mobile.jetweatherapp.repository.WeatherDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val weatherDBRepository: WeatherDBRepository) :
    ViewModel() {

    private val _unitList = MutableStateFlow<List<Units>>(emptyList())
    val unitList = _unitList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            weatherDBRepository.getUnits().distinctUntilChanged().collect { listOfUnit ->
                if (listOfUnit.isNotEmpty()) {
                    _unitList.value = listOfUnit
                }

            }
        }
    }

    fun insertUnits(units: Units) =
        viewModelScope.launch { weatherDBRepository.insertUnits(units) }

    fun deleteAllUnits() =
        viewModelScope.launch { weatherDBRepository.deleteAllUnits() }

}