package com.example.weatherappcompose.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.weatherappcompose.domain.models.WeatherModel
import com.example.weatherappcompose.ui.theme.BlueLight

// Мемоизированные стили для лучшей производительности
private val timeTextStyle = TextStyle(
    fontSize = 18.sp,
    color = Color.White
)

private val conditionTextStyle = TextStyle(
    fontSize = 22.sp,
    color = Color.White
)

private val tempTextStyle = TextStyle(
    fontSize = 25.sp,
    color = Color.White,
    fontWeight = FontWeight.Bold
)

@Composable
fun WeatherList(
    list: List<WeatherModel>,
    onItemClick: (WeatherModel) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 4.dp)
    ) {
        itemsIndexed(
            items = list,
            key = { index, item -> item.uniqueKey }
        ) { _, item ->
            WeatherListItem(
                item = item,
                onClick = { onItemClick(item) }
            )
        }
    }
}

@Composable
fun WeatherListItem(
    item: WeatherModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
            .padding(top = 3.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = BlueLight),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(
                    start = 8.dp,
                    top = 5.dp,
                    bottom = 5.dp
                )
            ) {
                Text(
                    style = timeTextStyle,
                    text = item.time
                )
                Text(
                    style = conditionTextStyle,
                    text = item.condition,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }

            Text(
                text = item.temperatureDisplayText,
                style = tempTextStyle
            )

            // Оптимизированная загрузка изображений с кэшированием
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .crossfade(true)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = "Weather icon",
                modifier = Modifier
                    .size(45.dp)
                    .padding(end = 8.dp)
            )
        }
    }
}
