package dzianis.trakhimik.weatherapp.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import dzianis.trakhimik.weatherapp.R
import dzianis.trakhimik.weatherapp.presentation.WeatherState
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun WeatherCard(
    state: WeatherState,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    var isDataExists = false
    Card(
        backgroundColor = backgroundColor,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.padding(16.dp)
    ) {
        state?.weatherInfo?.currentWeatherData?.let { data ->
            isDataExists = true
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = CenterHorizontally
            ) {
                Text(
                    text = "Today ${
                        data.time.format(DateTimeFormatter.ofPattern("HH:mm"))
                    }",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(End),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = data.weatherType.iconRes),
                    contentDescription = stringResource(id = data.weatherType.weatherDescResId),
                    modifier = Modifier.width(200.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${data.temperatureCelsius.roundToInt()} C / ${data.temperatureFahrenheit.roundToInt()} F",
                    style = MaterialTheme.typography.h4
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(id = data.weatherType.weatherDescResId),
                    style = MaterialTheme.typography.subtitle1
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherDataDisplay(
                        value = data.pressure.roundToInt(),
                        unit = "hpa",
                        icon = ImageVector.vectorResource(id = R.drawable.ic_pressure),
                        tintColor = Color.White,
                        textStyle = TextStyle(color = Color.White)
                    )
                    WeatherDataDisplay(
                        value = data.humidity,
                        unit = "%",
                        icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
                        tintColor = Color.White,
                        textStyle = TextStyle(color = Color.White)
                    )
                    WeatherDataDisplay(
                        value = data.windSpeed.roundToInt(),
                        unit = "km/h (${data.windSpeedInMiles.roundToInt()}m/h)",
                        icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
                        tintColor = Color.White,
                        textStyle = TextStyle(color = Color.White)
                    )
                }
            }
        }
        if (!isDataExists) {
            Text(
                text = "Loading...",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(CenterHorizontally)
            )
        }

    }

}