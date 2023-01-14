package dzianis.trakhimik.weatherapp.domain.utils

import java.time.format.TextStyle
import java.util.*

object TimeZoneHelper {
    fun gteCurrentTimeZone(): String {
        return TimeZone.getDefault().toZoneId().getDisplayName(TextStyle.NARROW, Locale.ENGLISH)
    }
}