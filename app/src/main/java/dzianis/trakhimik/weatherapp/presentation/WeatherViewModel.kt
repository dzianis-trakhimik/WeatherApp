package dzianis.trakhimik.weatherapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dzianis.trakhimik.weatherapp.domain.location.LocationTracker
import dzianis.trakhimik.weatherapp.domain.repository.WeatherRepository
import dzianis.trakhimik.weatherapp.domain.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {
    var state by mutableStateOf(WeatherState())
    private set

    fun loadWeatherInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let { location ->
                when (val result = repository.getWeatherData(location.latitude, location.longitude)) {
                    is Resource.Success -> {
                        state = WeatherState(
                            weatherInfo = result.data
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            } ?: kotlin.run {
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location. Check permissions for location and enable GPS"
                )
            }
        }
    }
}