package dzianis.trakhimik.weatherapp.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dzianis.trakhimik.weatherapp.domain.weather.WeatherData
import dzianis.trakhimik.weatherapp.presentation.WeatherState

@Composable
fun WeatherForeCast(
    forecast: List<WeatherData>,
    dayName: String? = null,
    modifier: Modifier = Modifier
) {
    val dayName = if (dayName == null ) remember(forecast) {
        forecast.firstOrNull()?.time?.dayOfWeek?.name ?: "undefined"
    } else dayName
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = dayName,
            style = MaterialTheme.typography.subtitle1.copy(
                color = Color.White
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow {
            items(forecast) { hourData ->
                HourlyWeatherDataDisplay(
                    weatherData = hourData,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}