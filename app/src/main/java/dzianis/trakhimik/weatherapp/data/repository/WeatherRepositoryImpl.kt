package dzianis.trakhimik.weatherapp.data.repository

import android.content.Context
import android.content.res.Resources
import dzianis.trakhimik.weatherapp.R
import dzianis.trakhimik.weatherapp.data.mappers.toWeatherInfo
import dzianis.trakhimik.weatherapp.data.remote.WeatherApi
import dzianis.trakhimik.weatherapp.domain.repository.WeatherRepository
import dzianis.trakhimik.weatherapp.domain.utils.Resource
import dzianis.trakhimik.weatherapp.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val context: Context
) : WeatherRepository {
    override suspend fun getWeatherData(latitude: Double, longitude: Double, timeZone: String): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(
                    latitude = latitude,
                    longitude = longitude,
                    timeZone = timeZone
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: context.getResources().getString(R.string.unknown_error))
        }
    }
}