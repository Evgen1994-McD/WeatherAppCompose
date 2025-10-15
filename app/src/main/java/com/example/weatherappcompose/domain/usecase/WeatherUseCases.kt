package com.example.weatherappcompose.domain.usecase

import com.example.weatherappcompose.domain.models.WeatherModel
import com.example.weatherappcompose.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherByCityUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(city: String): Result<List<WeatherModel>> {
        return weatherRepository.getWeatherByCity(city)
    }
}

class GetWeatherByHoursUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(hours: String): Result<List<WeatherModel>> {
        return weatherRepository.getWeatherByHours(hours)
    }
}
