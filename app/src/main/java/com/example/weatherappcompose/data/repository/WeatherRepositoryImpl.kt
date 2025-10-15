package com.example.weatherappcompose.data.repository

import com.example.weatherappcompose.data.datasource.WeatherRemoteDataSource
import com.example.weatherappcompose.data.mapper.WeatherMapper
import com.example.weatherappcompose.domain.models.WeatherModel
import com.example.weatherappcompose.domain.repository.WeatherRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource
) : WeatherRepository {
    
    override suspend fun getWeatherByCity(city: String): Result<List<WeatherModel>> {
        return try {
            val response = remoteDataSource.getWeatherData(city)
            val weatherModels = WeatherMapper.mapWeatherResponseToModels(response)
            Result.success(weatherModels)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getWeatherByHours(hours: String): Result<List<WeatherModel>> {
        return try {
            val weatherModels = WeatherMapper.mapHoursToModels(hours)
            Result.success(weatherModels)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
