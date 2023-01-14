package dzianis.trakhimik.weatherapp.domain.repository

import dzianis.trakhimik.weatherapp.domain.utils.Resource
import dzianis.trakhimik.weatherapp.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(
        latitude: Double,
        longitude: Double,
        timezone: String
    ): Resource<WeatherInfo>
}