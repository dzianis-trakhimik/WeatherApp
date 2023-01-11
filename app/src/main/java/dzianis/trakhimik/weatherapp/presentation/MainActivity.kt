package dzianis.trakhimik.weatherapp.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import dzianis.trakhimik.weatherapp.domain.weather.WeatherData
import dzianis.trakhimik.weatherapp.presentation.ui.components.WeatherCard
import dzianis.trakhimik.weatherapp.presentation.ui.components.WeatherForeCast
import dzianis.trakhimik.weatherapp.ui.theme.DarkBlue
import dzianis.trakhimik.weatherapp.ui.theme.WeatherAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.loadWeatherInfo()
        }
        permissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ))
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(state = viewModel.state)
                }
            }
        }
    }
}

@Composable
fun MainScreen(state: WeatherState) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        }
        if (state.error?.isNotEmpty() ?: false) {
            Text(text = "ERROR: ${state.error}")
        }
        if (state.weatherInfo != null) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    WeatherCard(
                        state = state,
                        backgroundColor = DarkBlue,
                        modifier = Modifier
                    )
                }
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        backgroundColor = DarkBlue,
                        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                        elevation = 6.dp
                    ) {
                        WeatherForeCast(
                            dayName = "TODAY",
                            forecast = state.weatherInfo.weatherDataPerDay.get(0) ?: listOf()
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                items(
                    state.weatherInfo.weatherDataPerDay.filter { it.key > 0 }.values.toList<List<WeatherData>>()
                        ?: listOf<List<WeatherData>>()
                ) { dayForecast ->
                    WeatherForeCast(dayForecast)
                }
            }
        }
    }

}