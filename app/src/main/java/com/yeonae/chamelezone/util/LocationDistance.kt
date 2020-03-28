package com.yeonae.chamelezone.util

import android.location.Location

fun distanceByDegree(
    currentLatitude: Double?,
    currentLongitude: Double?,
    latitude: Double,
    longitude: Double
): String {
    val startPos = Location("PointA")
    val endPos = Location("PointB")

    if (currentLatitude != null && currentLongitude != null) {
        startPos.latitude = currentLatitude
        startPos.longitude = currentLongitude
    }

    endPos.latitude = latitude
    endPos.longitude = longitude

    val distance = startPos.distanceTo(endPos) / 1000

    return "$distance km"
}
