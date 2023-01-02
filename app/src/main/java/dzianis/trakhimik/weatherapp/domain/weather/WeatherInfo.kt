package dzianis.trakhimik.weatherapp.domain.weather

data class WeatherInfo(
    val currentWeatherData: WeatherData?,
    val weatherDataPerDay: Map<Int, List<WeatherData>>
)
