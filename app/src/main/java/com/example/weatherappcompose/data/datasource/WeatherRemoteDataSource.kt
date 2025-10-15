package com.example.weatherappcompose.data.datasource

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(
    private val context: Context
) {
    companion object {
        private const val API_KEY = "0b943d620b224bfcb3062549250410"
        private const val BASE_URL = "https://api.weatherapi.com/v1/forecast.json"
    }

    suspend fun getWeatherData(city: String): String {
        return suspendCancellableCoroutine { continuation ->
            val url = "$BASE_URL?key=$API_KEY&q=$city&days=10&aqi=no"
            
            val stringRequest = StringRequest(
                Request.Method.GET,
                url,
                { response ->
                    continuation.resume(response)
                },
                { error ->
                    continuation.resumeWithException(error)
                }
            )

            val queue = Volley.newRequestQueue(context)
            queue.add(stringRequest)

            continuation.invokeOnCancellation {
                stringRequest.cancel()
            }
        }
    }
}
