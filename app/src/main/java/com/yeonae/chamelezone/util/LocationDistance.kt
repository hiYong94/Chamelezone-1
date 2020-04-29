package com.yeonae.chamelezone.util

import android.location.Location
import kotlin.math.roundToInt

fun distanceByDegree(
    currentLatitude: Double,
    currentLongitude: Double,
    latitude: Double,
    longitude: Double
): String {
    val startPos = Location("PointA")
    val endPos = Location("PointB")

    startPos.latitude = currentLatitude
    startPos.longitude = currentLongitude

    endPos.latitude = latitude
    endPos.longitude = longitude

    val distance = startPos.distanceTo(endPos) / 1000

    return "${(distance * 100).roundToInt() /100.0} km"
}
