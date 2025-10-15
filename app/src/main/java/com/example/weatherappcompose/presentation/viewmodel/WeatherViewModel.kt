package com.example.weatherappcompose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappcompose.domain.models.WeatherModel
import com.example.weatherappcompose.domain.usecase.GetWeatherByCityUseCase
import com.example.weatherappcompose.domain.usecase.GetWeatherByHoursUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherByCityUseCase: GetWeatherByCityUseCase,
    private val getWeatherByHoursUseCase: GetWeatherByHoursUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()
    
    fun loadWeatherByCity(city: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            getWeatherByCityUseCase(city)
                .onSuccess { weatherList ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        weatherList = weatherList,
                        currentDay = weatherList.firstOrNull() ?: WeatherModel("", "", "", "", "", "", "", ""),
                        error = null
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Unknown error"
                    )
                }
        }
    }
    
    fun loadWeatherByHours(hours: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            getWeatherByHoursUseCase(hours)
                .onSuccess { weatherList ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        weatherList = weatherList,
                        error = null
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Unknown error"
                    )
                }
        }
    }
    
    fun updateCurrentDay(weatherModel: WeatherModel) {
        _uiState.value = _uiState.value.copy(currentDay = weatherModel)
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class WeatherUiState(
    val isLoading: Boolean = false,
    val weatherList: List<WeatherModel> = emptyList(),
    val currentDay: WeatherModel = WeatherModel("", "", "", "", "", "", "", ""),
    val error: String? = null
)
