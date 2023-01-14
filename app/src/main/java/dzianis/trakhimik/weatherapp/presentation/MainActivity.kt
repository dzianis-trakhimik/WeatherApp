package dzianis.trakhimik.weatherapp.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint
import dzianis.trakhimik.weatherapp.R
import dzianis.trakhimik.weatherapp.domain.weather.WeatherData
import dzianis.trakhimik.weatherapp.presentation.ui.components.WeatherCard
import dzianis.trakhimik.weatherapp.presentation.ui.components.WeatherForeCast
import dzianis.trakhimik.weatherapp.ui.theme.DarkBlue
import dzianis.trakhimik.weatherapp.ui.theme.DarkGreen
import dzianis.trakhimik.weatherapp.ui.theme.WeatherAppTheme
import kotlinx.coroutines.launch
import java.util.*

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
                    MainScreen(
                        state = viewModel.state,
                        onRefresh = viewModel::loadWeatherInfo
                    )
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    state: WeatherState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scaffoldState = rememberScaffoldState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isLoading)
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    backgroundColor = DarkGreen,
                    contentColor = MaterialTheme.colors.onSurface,
                    snackbarData = data
                )
            }
        }
    ) {
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = onRefresh,
            modifier = Modifier
                .fillMaxSize()
        ) {
            WeatherForWeek(
                state = state,
                onShowSnackBar = {
                    scope.launch { scaffoldState.snackbarHostState.showSnackbar(it) }
                },
                onRefresh = onRefresh
            )
        }
    }


}

@Composable
fun WeatherForWeek(state: WeatherState, onShowSnackBar: (String) -> Unit, onRefresh: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        state.error?.let {
            if (state.weatherInfo == null) {
                IconButton(
                    onClick = onRefresh
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = stringResource(id = R.string.refresh),
                        modifier = Modifier.size(64.dp)
                    )
                }
            }
            onShowSnackBar(stringResource(id = R.string.common_error))
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
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        elevation = 6.dp
                    ) {
                        WeatherForeCast(
                            dayName = stringResource(id = R.string.today).uppercase(Locale.ROOT),
                            forecast = state.weatherInfo.weatherDataPerDay.get(0) ?: listOf()
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                items(
                    state.weatherInfo.weatherDataPerDay.filter { it.key > 0 }.values.toList()
                ) { dayForecast ->
                    WeatherForeCast(dayForecast)
                }
            }
        }
    }

}