package com.example.weatherappcompose.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.weatherappcompose.R
import com.example.weatherappcompose.domain.models.WeatherModel
import com.example.weatherappcompose.presentation.viewmodel.WeatherViewModel
import com.example.weatherappcompose.ui.theme.BlueLight
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.json.JSONArray

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
    onSearchClick: () -> Unit,
    onSyncClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
        MainCard(
            currentDay = uiState.currentDay,
            onclickSync = onSyncClick,
            onclickSearch = onSearchClick
        )

        val tabList = listOf("Сейчас", "Дни")
        val tabIndex = pagerState.currentPage

        PrimaryTabRow(
            selectedTabIndex = tabIndex,
            modifier = Modifier.fillMaxWidth(),
            containerColor = BlueLight,
            contentColor = Color.White
        ) {
            tabList.forEachIndexed { index, text ->
                Tab(
                    selected = tabIndex == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { 
                        Text(
                            text = text,
                            color = Color.White,
                            style = TextStyle(fontSize = 16.sp)
                        ) 
                    }
                )
            }
        }

        HorizontalPager(
            count = tabList.size,
            state = pagerState,
            modifier = Modifier.weight(1.0f)
        ) { index ->
            when (index) {
                0 -> {
                    val hoursList = getWeatherByHours(uiState.currentDay.hours)
                    WeatherList(
                        list = hoursList,
                        onItemClick = { /* Handle hour click if needed */ }
                    )
                }
                1 -> {
                    WeatherList(
                        list = uiState.weatherList,
                        onItemClick = { weatherModel ->
                            viewModel.updateCurrentDay(weatherModel)
                        }
                    )
                }
            }
        }
        }

        // Показываем индикатор загрузки
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(50.dp),
                color = Color.White
            )
        }

        // Показываем ошибку если есть
        uiState.error?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun MainCard(
    currentDay: WeatherModel,
    onclickSync: () -> Unit,
    onclickSearch: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        colors = CardDefaults.cardColors(containerColor = BlueLight),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Верхний ряд: время слева, иконка погоды справа
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                    text = currentDay.time,
                    style = TextStyle(
                        fontSize = 24.sp,
                        color = Color.White
                    )
                )
                AsyncImage(
                    model = currentDay.imageUrl,
                    contentDescription = "Weather icon",
                    modifier = Modifier
                        .size(70.dp)
                        .padding(top = 3.dp, end = 8.dp)
                )
            }

            // Город по центру
            Text(
                text = currentDay.city,
                style = TextStyle(
                    fontSize = 24.sp,
                    color = Color.White
                )
            )

            // Температура по центру
            Text(
                text = if (currentDay.currentTemp.isNotEmpty()) {
                    currentDay.currentTemp + "°C"
                } else {
                    "${currentDay.maxTemp}°/${currentDay.minTemp}°"
                },
                style = TextStyle(
                    fontSize = 65.sp,
                    color = Color.White
                )
            )

            // Состояние по центру
            Text(
                text = currentDay.condition,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White
                )
            )

            // Нижний ряд: поиск слева, температура по центру, refresh справа
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    modifier = Modifier
                        .size(32.dp)
                        .padding(6.dp),
                    onClick = onclickSearch
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search",
                        tint = Color.White
                    )
                }

                Text(
                    modifier = Modifier.padding(top = 6.dp),
                    text = currentDay.maxTemp + "°C/" + currentDay.minTemp + "°C",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White
                    )
                )

                IconButton(
                    modifier = Modifier.size(32.dp),
                    onClick = onclickSync
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.refresh),
                        contentDescription = "Sync",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

private fun getWeatherByHours(hours: String): List<WeatherModel> {
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
