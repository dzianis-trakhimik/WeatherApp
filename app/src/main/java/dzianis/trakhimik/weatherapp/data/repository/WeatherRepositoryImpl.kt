package dzianis.trakhimik.weatherapp.data.repository

import dzianis.trakhimik.weatherapp.data.mappers.toWeatherInfo
import dzianis.trakhimik.weatherapp.data.remote.WeatherApi
import dzianis.trakhimik.weatherapp.domain.repository.WeatherRepository
import dzianis.trakhimik.weatherapp.domain.utils.Resource
import dzianis.trakhimik.weatherapp.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {
    override suspend fun getWeatherData(latitude: Double, longitude: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(
                    latitude = latitude,
                    longitude = longitude
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "An unknown error occurred")
        }
    }
}