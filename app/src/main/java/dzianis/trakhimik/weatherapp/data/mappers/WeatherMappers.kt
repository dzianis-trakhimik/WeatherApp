package dzianis.trakhimik.weatherapp.data.mappers

import dzianis.trakhimik.weatherapp.data.remote.WeatherDataDto
import dzianis.trakhimik.weatherapp.data.remote.WeatherDto
import dzianis.trakhimik.weatherapp.domain.utils.TemperatureConverter
import dzianis.trakhimik.weatherapp.domain.weather.WeatherData
import dzianis.trakhimik.weatherapp.domain.weather.WeatherInfo
import dzianis.trakhimik.weatherapp.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                temperatureFahrenheit = TemperatureConverter.celsiusToFahrenheit(temperature),
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.getByWetherCode(weatherCode)
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { it.data }
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if (now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }
    return WeatherInfo(
        currentWeatherData = currentWeatherData,
        weatherDataPerDay = weatherDataMap
    )
}