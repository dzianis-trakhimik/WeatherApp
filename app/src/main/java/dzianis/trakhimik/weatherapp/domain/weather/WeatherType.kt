package dzianis.trakhimik.weatherapp.domain.weather

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import dzianis.trakhimik.weatherapp.R

sealed class WeatherType(
    @StringRes val weatherDescResId: Int,
    @DrawableRes val iconRes: Int
) {
    object ClearSky : WeatherType(
        weatherDescResId = R.string.clear_sky,
        iconRes = R.drawable.ic_sunny
    )
    object MainlyClear : WeatherType(
        weatherDescResId = R.string.mainly_clear,
        iconRes = R.drawable.ic_cloudy
    )
    object PartlyCloudy : WeatherType(
        weatherDescResId = R.string.partly_cloudy,
        iconRes = R.drawable.ic_cloudy
    )
    object Overcast : WeatherType(
        weatherDescResId = R.string.overcast,
        iconRes = R.drawable.ic_cloudy
    )
    object Foggy : WeatherType(
        weatherDescResId = R.string.foggy,
        iconRes = R.drawable.ic_very_cloudy
    )
    object DepositingRimeFog : WeatherType(
        weatherDescResId = R.string.depositing_rime_fog,
        iconRes = R.drawable.ic_very_cloudy
    )
    object LightDrizzle : WeatherType(
        weatherDescResId = R.string.light_drizzle,
        iconRes = R.drawable.ic_rainshower
    )
    object ModerateDrizzle : WeatherType(
        weatherDescResId = R.string.moderate_drizzle,
        iconRes = R.drawable.ic_rainshower
    )
    object DenseDrizzle : WeatherType(
        weatherDescResId = R.string.dense_drizzle,
        iconRes = R.drawable.ic_rainshower
    )
    object LightFreezingDrizzle : WeatherType(
        weatherDescResId = R.string.slight_freezing_drizzle,
        iconRes = R.drawable.ic_snowyrainy
    )
    object DenseFreezingDrizzle : WeatherType(
        weatherDescResId = R.string.dense_freezing_drizzle,
        iconRes = R.drawable.ic_snowyrainy
    )
    object SlightRain : WeatherType(
        weatherDescResId = R.string.slight_rain,
        iconRes = R.drawable.ic_rainy
    )
    object ModerateRain : WeatherType(
        weatherDescResId = R.string.rainy,
        iconRes = R.drawable.ic_rainy
    )
    object HeavyRain : WeatherType(
        weatherDescResId = R.string.heavy_rain,
        iconRes = R.drawable.ic_rainy
    )
    object HeavyFreezingRain: WeatherType(
        weatherDescResId = R.string.heavy_freezing_raine,
        iconRes = R.drawable.ic_snowyrainy
    )
    object SlightSnowFall: WeatherType(
        weatherDescResId = R.string.slight_snow_fall,
        iconRes = R.drawable.ic_snowy
    )
    object ModerateSnowFall: WeatherType(
        weatherDescResId = R.string.moderate_snow_fall,
        iconRes = R.drawable.ic_heavysnow
    )
    object HeavySnowFall: WeatherType(
        weatherDescResId = R.string.heavy_snow_fall,
        iconRes = R.drawable.ic_heavysnow
    )
    object SnowGrains: WeatherType(
        weatherDescResId = R.string.snow_grains,
        iconRes = R.drawable.ic_heavysnow
    )
    object SlightRainShowers: WeatherType(
        weatherDescResId = R.string.slight_rain_showers,
        iconRes = R.drawable.ic_rainshower
    )
    object ModerateRainShowers: WeatherType(
        weatherDescResId = R.string.moderate_rain_showers,
        iconRes = R.drawable.ic_rainshower
    )
    object ViolentRainShowers: WeatherType(
        weatherDescResId = R.string.violent_rain_showers,
        iconRes = R.drawable.ic_rainshower
    )
    object SlightSnowShowers: WeatherType(
        weatherDescResId = R.string.light_snow_showers,
        iconRes = R.drawable.ic_snowy
    )
    object HeavySnowShowers: WeatherType(
        weatherDescResId = R.string.heavy_snow_showers,
        iconRes = R.drawable.ic_snowy
    )
    object ModerateThunderstorm: WeatherType(
        weatherDescResId = R.string.moderate_thunderstorm,
        iconRes = R.drawable.ic_thunder
    )
    object SlightHailThunderstorm: WeatherType(
        weatherDescResId = R.string.thunderstorm_with_slight_hail,
        iconRes = R.drawable.ic_rainythunder
    )
    object HeavyHailThunderstorm: WeatherType(
        weatherDescResId = R.string.thunderstorm_with_heavy_hail,
        iconRes = R.drawable.ic_rainythunder
    )

    companion object {
        fun getByWetherCode(code: Int): WeatherType {
            return when(code) {
                0 -> ClearSky
                1 -> MainlyClear
                2 -> PartlyCloudy
                3 -> Overcast
                45 -> Foggy
                48 -> DepositingRimeFog
                51 -> LightDrizzle
                53 -> ModerateDrizzle
                55 -> DenseDrizzle
                56 -> LightFreezingDrizzle
                57 -> DenseFreezingDrizzle
                61 -> SlightRain
                63 -> ModerateRain
                65 -> HeavyRain
                66 -> LightFreezingDrizzle
                67 -> HeavyFreezingRain
                71 -> SlightSnowFall
                73 -> ModerateSnowFall
                75 -> HeavySnowFall
                77 -> SnowGrains
                80 -> SlightRainShowers
                81 -> ModerateRainShowers
                82 -> ViolentRainShowers
                85 -> SlightSnowShowers
                86 -> HeavySnowShowers
                95 -> ModerateThunderstorm
                96 -> SlightHailThunderstorm
                99 -> HeavyHailThunderstorm
                else -> ClearSky
            }
        }
    }
}