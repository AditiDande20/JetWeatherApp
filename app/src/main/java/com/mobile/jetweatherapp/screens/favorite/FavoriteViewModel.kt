package com.mobile.jetweatherapp.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.jetweatherapp.model.Favorite
import com.mobile.jetweatherapp.repository.WeatherDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val weatherDBRepository: WeatherDBRepository) :
    ViewModel() {

    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            weatherDBRepository.getFavorites().distinctUntilChanged().collect { listOFav ->
                if (listOFav.isNotEmpty()) {
                    _favList.value = listOFav
                }

            }
        }
    }

    fun insertFavorite(favorite: Favorite) =
        viewModelScope.launch { weatherDBRepository.insertFavorite(favorite = favorite) }

    fun deleteFavorite(favorite: Favorite) =
        viewModelScope.launch { weatherDBRepository.deleteFavorite(favorite = favorite) }

}