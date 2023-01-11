package dzianis.trakhimik.weatherapp.domain.weather

import java.time.LocalDateTime

data class WeatherData(
    val time: LocalDateTime,
    val temperatureCelsius: Double,
    val temperatureFahrenheit: Double,
    val humidity: Int,
    val pressure: Double,
    val windSpeed: Double,
    val windSpeedInMiles: Double,
    val weatherType: WeatherType
)
