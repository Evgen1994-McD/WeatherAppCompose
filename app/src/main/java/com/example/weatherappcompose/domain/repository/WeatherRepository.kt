package com.example.weatherappcompose.domain.repository

import com.example.weatherappcompose.domain.models.WeatherModel

interface WeatherRepository {
    suspend fun getWeatherByCity(city: String): Result<List<WeatherModel>>
    suspend fun getWeatherByHours(hours: String): Result<List<WeatherModel>>
}
