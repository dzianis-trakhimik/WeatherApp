package dzianis.trakhimik.weatherapp.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import dzianis.trakhimik.weatherapp.presentation.ui.components.WeatherCard
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
    if (state.isLoading) {
        Text(text = "LOADING...")
    }
    if (state.error?.isNotEmpty() ?: false) {
        Text(text = "ERROR: ${state.error}")
    }
    if (state.weatherInfo != null) {
        WeatherCard(
            state = state,
            backgroundColor = Color.DarkGray,
            modifier = Modifier.fillMaxSize()
        )
    }
}