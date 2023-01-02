package dzianis.trakhimik.weatherapp.domain.utils

object TemperatureConverter {
    fun celsiusToFahrenheit(tempInCelsius: Double): Double {
        return tempInCelsius * 9.0 / 5 + 32
    }

    fun fahrenheitToCelsius(tempInForengheiht: Double): Double {
        return (tempInForengheiht - 32) * 5.0 / 9
    }
}