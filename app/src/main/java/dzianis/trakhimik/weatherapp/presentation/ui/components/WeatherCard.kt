package dzianis.trakhimik.weatherapp.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dzianis.trakhimik.weatherapp.domain.weather.WeatherData
import dzianis.trakhimik.weatherapp.presentation.WeatherState
import java.time.format.DateTimeFormatter

@Composable
fun WeatherCard(
    state: WeatherState,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    if (state.weatherInfo?.currentWeatherData == null) {
        return Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(CenterHorizontally),
            fontSize = 22.sp,
            text = "No data..."
        )
    }

    state.weatherInfo.currentWeatherData.let { data ->
        Card(
            backgroundColor = backgroundColor,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
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
                Text(text = "${data.temperatureCelsius} C")
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "${data.temperatureFahrenheit} F")

                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn {
                    val days = state.weatherInfo?.weatherDataPerDay ?: mapOf<Int, List<WeatherData>>()
                    items(days.keys.toList()) { dayIndex ->
                        val dayItem = days[dayIndex]
                        Column(modifier = Modifier.fillMaxWidth().padding(8.dp).border(2.dp, Color.Black, RoundedCornerShape(6.dp))) {
                            Text(" ${(data.time.plusDays(dayIndex.toLong())).format(DateTimeFormatter.ofPattern("dd MMM"))} ")
                            dayItem?.forEach { hour ->
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Text(" ${hour.time.format(DateTimeFormatter.ofPattern("HH:mm"))} ")
                                    Image(
                                        painter = painterResource(id = hour.weatherType.iconRes),
                                        contentDescription = null,
                                        modifier = Modifier.width(50.dp)
                                    )
                                    Text("${hour.temperatureCelsius} C / ${hour.temperatureFahrenheit} F")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}