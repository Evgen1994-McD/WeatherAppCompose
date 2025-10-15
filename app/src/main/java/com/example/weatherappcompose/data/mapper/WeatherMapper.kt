package com.example.weatherappcompose.data.mapper

import com.example.weatherappcompose.domain.models.WeatherModel
import org.json.JSONArray
import org.json.JSONObject

object WeatherMapper {
    
    fun mapWeatherResponseToModels(response: String): List<WeatherModel> {
        if (response.isEmpty()) return emptyList()
        
        return try {
            val mainObject = JSONObject(response)
            val list = ArrayList<WeatherModel>()
            val city = mainObject.getJSONObject("location").getString("name")
            val days = mainObject.getJSONObject("forecast").getJSONArray("forecastday")
            
            for (i in 0 until days.length()) {
                val item = days.getJSONObject(i)
                list.add(
                    WeatherModel(
                        city = city,
                        time = item.getString("date"),
                        currentTemp = "",
                        condition = item.getJSONObject("day").getJSONObject("condition").getString("text"),
                        icon = item.getJSONObject("day").getJSONObject("condition").getString("icon"),
                        maxTemp = item.getJSONObject("day").getString("maxtemp_c"),
                        minTemp = item.getJSONObject("day").getString("mintemp_c"),
                        hours = item.getJSONArray("hour").toString()
                    )
                )
            }
            
            // Обновляем первый элемент текущими данными
            if (list.isNotEmpty()) {
                list[0] = list[0].copy(
                    time = mainObject.getJSONObject("current").getString("last_updated"),
                    currentTemp = mainObject.getJSONObject("current").getString("temp_c")
                )
            }
            
            list
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    fun mapHoursToModels(hours: String): List<WeatherModel> {
        if (hours.isEmpty()) return emptyList()
        
        return try {
            val hoursArray = JSONArray(hours)
            val list = ArrayList<WeatherModel>(hoursArray.length())
            
            for (i in 0 until hoursArray.length()) {
                val item = hoursArray.getJSONObject(i)
                val condition = item.getJSONObject("condition")
                
                list.add(
                    WeatherModel(
                        city = "",
                        time = item.getString("time"),
                        currentTemp = item.getString("temp_c"),
                        condition = condition.getString("text"),
                        icon = condition.getString("icon"),
                        maxTemp = "",
                        minTemp = "",
                        hours = ""
                    )
                )
            }
            list
        } catch (e: Exception) {
            emptyList()
        }
    }
}
