package com.example.weatherappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.weatherappcompose.presentation.ui.SearchDialog
import com.example.weatherappcompose.presentation.ui.WeatherScreen
import com.example.weatherappcompose.presentation.viewmodel.WeatherViewModel
import com.example.weatherappcompose.ui.theme.WeatherAppComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppComposeTheme {
                var dialogState by remember { mutableStateOf(false) }
                
                // Временно создаем ViewModel без Hilt
                val viewModel = WeatherViewModel(
                    getWeatherByCityUseCase = com.example.weatherappcompose.domain.usecase.GetWeatherByCityUseCase(
                        weatherRepository = com.example.weatherappcompose.data.repository.WeatherRepositoryImpl(
                            remoteDataSource = com.example.weatherappcompose.data.datasource.WeatherRemoteDataSource(this)
                        )
                    ),
                    getWeatherByHoursUseCase = com.example.weatherappcompose.domain.usecase.GetWeatherByHoursUseCase(
                        weatherRepository = com.example.weatherappcompose.data.repository.WeatherRepositoryImpl(
                            remoteDataSource = com.example.weatherappcompose.data.datasource.WeatherRemoteDataSource(this)
                        )
                    )
                )

                // Загружаем данные для Перми по умолчанию
                viewModel.loadWeatherByCity("Perm")

                Image(
                    painter = painterResource(id = R.drawable.clouds_bg),
                    contentDescription = "Background",
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.7f),
                    contentScale = ContentScale.FillBounds
                )

                Column {
                    WeatherScreen(
                        viewModel = viewModel,
                        onSearchClick = { dialogState = true },
                        onSyncClick = { 
                            // Получаем текущий город из состояния и обновляем данные
                            val currentCity = viewModel.uiState.value.currentDay.city
                            if (currentCity.isNotEmpty()) {
                                viewModel.loadWeatherByCity(currentCity)
                            }
                        }
                    )
                }

                if (dialogState) {
                    SearchDialog(
                        dialogState = remember { mutableStateOf(dialogState) },
                        onSubmit = { city ->
                            viewModel.loadWeatherByCity(city)
                            dialogState = false
                        }
                    )
                }
            }
        }
    }
}