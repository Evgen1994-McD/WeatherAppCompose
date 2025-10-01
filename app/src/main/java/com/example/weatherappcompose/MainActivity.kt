package com.example.weatherappcompose

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherappcompose.ui.theme.WeatherAppComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val state = remember {
        mutableStateOf("Unknown")
    }

    Column(modifier = Modifier
        .fillMaxSize()) {

        Box(modifier= Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Temp in $name = ${state.value}"
            )
        }
        Box(modifier= Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ){
            Button(onClick = {
getResult()
            },
                modifier = Modifier
                    .padding(5.dp,
                         bottom = 80.dp)
                    .fillMaxWidth()

            ) {
                Text(
                    text = "Refresh"
                )
            }
        }
    }
}

private fun getResult(city:String, state:MutableState<String> ,context: Context){
    //
}
