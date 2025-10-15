package com.example.weatherappcompose.domain.models

data class WeatherModel(
    val city: String,
    val time : String,
    val currentTemp: String,
    val condition: String,
    val icon: String,
    val maxTemp: String,
    val minTemp: String,
    val hours: String
) {
    // Кэшированные вычисляемые свойства для лучшей производительности
    val temperatureDisplayText: String
        get() = if (currentTemp.isNotEmpty()) {
            "${currentTemp}°С"
        } else {
            "${maxTemp}°С/${minTemp}°С"
        }
    
    val imageUrl: String
        get() = "https:$icon"
    
    val uniqueKey: String
        get() = "${time}_${city}_${currentTemp}_${maxTemp}_${minTemp}"
}
