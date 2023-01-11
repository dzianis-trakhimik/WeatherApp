package dzianis.trakhimik.weatherapp.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dzianis.trakhimik.weatherapp.domain.weather.WeatherData
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun HourlyWeatherDataDisplay(
    weatherData: WeatherData,
    modifier: Modifier = Modifier,
    color: Color = Color.White
) {
    val formattedTime = remember(weatherData) {
        weatherData.time.format(
            DateTimeFormatter.ofPattern("HH:mm")
        )
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = formattedTime,
            style = MaterialTheme.typography.body2,
            color = color
        )
        Image(painter = painterResource(
            id = weatherData.weatherType.iconRes),
            contentDescription = stringResource(id = weatherData.weatherType.weatherDescResId),
            modifier = Modifier.size(40.dp)
        )
        Text(
            text = "${weatherData.temperatureCelsius.roundToInt()}C\n${weatherData.temperatureFahrenheit.roundToInt()}F",
            style = MaterialTheme.typography.body2,
            color = color
        )
    }
}