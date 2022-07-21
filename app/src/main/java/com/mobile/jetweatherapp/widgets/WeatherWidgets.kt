package com.mobile.jetweatherapp.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.mobile.jetweatherapp.R
import com.mobile.jetweatherapp.model.currentWeather.Weather
import com.mobile.jetweatherapp.model.forecast.WeatherItem
import com.mobile.jetweatherapp.utils.formatDate
import com.mobile.jetweatherapp.utils.formatDateTime
import com.mobile.jetweatherapp.utils.formatDecimals
import java.util.*


@Composable
fun WeatherForecast(weatherForecast: com.mobile.jetweatherapp.model.forecast.Weather?) {
    Surface(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = Color(0xFFEFF1EF),
        shape = RoundedCornerShape(size = 15.dp)
    ) {
        LazyColumn(modifier = Modifier.padding(1.dp), contentPadding = PaddingValues(1.dp)) {

            if (weatherForecast != null) {
                items(items = weatherForecast.list) { item: WeatherItem ->
                    WeatherDetailRow(item)
                }
            }

        }

    }

}

@Composable
fun WeatherDetailRow(item: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${item.weather.first().icon}.png"
    Surface(
        modifier = Modifier
            .padding(1.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(1.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = formatDate(item.dt).split(",")[0], modifier = Modifier.padding(5.dp))
            WeatherStateImage(imageUrl = imageUrl)
            Surface(
                modifier = Modifier.padding(1.dp),
                shape = CircleShape,
                color = Color(0xFFFFC400)
            ) {
                Text(
                    text = item.weather.first().description,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(2.dp)
                )
            }
            Text(buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue.copy(alpha = 0.7f),
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append(formatDecimals(item.main.tempMax) + "°")
                }
                withStyle(style = SpanStyle(color = Color.LightGray)) {
                    append(formatDecimals(item.main.tempMin) + "°")
                }
            })

        }

    }


}

@Composable
fun SunriseAndSunsetRow(weather: Weather) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painterResource(id = R.drawable.ic_sunrise),
                contentDescription = "Sunrise",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = formatDateTime(weather.sys.sunrise).uppercase(Locale.ROOT),
                style = MaterialTheme.typography.caption
            )
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Text(
                text = formatDateTime(weather.sys.sunset).uppercase(Locale.ROOT),
                style = MaterialTheme.typography.caption
            )
            Icon(
                painterResource(id = R.drawable.ic_sunset),
                contentDescription = "Sunset",
                modifier = Modifier.size(20.dp)
            )

        }
    }

}

@Composable
fun WeatherHumidityPressureRow(weather: Weather, isImperial: Boolean) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painterResource(id = R.drawable.ic_humidity),
                contentDescription = "humidity",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = weather.main.humidity.toString() + "%",
                style = MaterialTheme.typography.caption
            )
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painterResource(id = R.drawable.ic_pressure),
                contentDescription = "pressure",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = weather.main.pressure.toString() + " psi",
                style = MaterialTheme.typography.caption
            )
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painterResource(id = R.drawable.ic_wind_speed),
                contentDescription = "speed",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = formatDecimals(weather.wind.speed) + if (isImperial) " mph" else " m/s",
                style = MaterialTheme.typography.caption
            )
        }
    }
}
