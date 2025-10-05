package com.example.weatherappcompose

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherappcompose.screens.MainCard
import com.example.weatherappcompose.screens.TabLayout
import org.json.JSONObject

const val API_KEY = "0b943d620b224bfcb3062549250410"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            getData("London", this)
            Image(
                painter = painterResource(id = R.drawable.clouds_bg),
                contentDescription = "im1",
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.7f),
                contentScale = ContentScale.FillBounds
            )

            Column {
                MainCard()
                TabLayout()


            }
        }
    }
}






private fun getData(city:String,context: Context){
val url = "https://api.weatherapi.com/v1/current.json"+
        "?key=$API_KEY&"+
        "q=$city"+
        "&days="+
        "3"+
        "&aqi=no"
val queue = Volley.newRequestQueue(context)
val stringRequest = StringRequest(
    Request.Method.GET,
    url,
    {
        response ->
        Log.d("MyLog", "Response $response")
        val obj = JSONObject(response)
    },
    {
        error ->
        Log.d("MyLog", "Error $error")
    }

)

    queue.add(stringRequest)
}
