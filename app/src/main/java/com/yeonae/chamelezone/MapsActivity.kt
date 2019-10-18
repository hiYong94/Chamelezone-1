package com.yeonae.chamelezone

import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback {

    private var mMap: GoogleMap? = null
    private var currentMarker: Marker? = null
    internal var mCurrentLocation: Location? = null
    internal lateinit var currentPosition: LatLng

    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var location: Location? = null

    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)

            val locationList = locationResult!!.locations

            if (locationList.size > 0) {
                location = locationList[locationList.size - 1]

                currentPosition = LatLng(location!!.latitude, location!!.longitude)

                val markerTitle = getCurrentAddress(currentPosition)
                val markerSnippet = ("위도:" + location!!.latitude.toString()
                        + " 경도:" + location!!.longitude.toString())

                setCurrentLocation(location, markerTitle, markerSnippet)

                mCurrentLocation = location
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        locationRequest = LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL_MS.toLong())
            .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS.toLong())

        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest!!)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        btn_search.setOnClickListener {
            mMap!!.clear()
            val geocoder = Geocoder(this, Locale.getDefault())
            val str = edt_search.text.toString()
            val addresses: List<Address>?
            addresses = geocoder.getFromLocationName(
                str,
                1
            )
            val latitude = addresses[0].latitude
            val longitude = addresses[0].longitude

            val place = LatLng(latitude, longitude)

            val markerOptions = MarkerOptions()
            markerOptions.position(place)
            markerOptions.title(str)
            mMap!!.addMarker(markerOptions)

            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(place))
            mMap!!.animateCamera(CameraUpdateFactory.zoomTo(15f))


        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        setDefaultLocation()
        startLocationUpdates()

        mMap!!.uiSettings.isMyLocationButtonEnabled = true
        mMap!!.animateCamera(CameraUpdateFactory.zoomTo(15f))
    }

    private fun startLocationUpdates() {
        mFusedLocationClient!!.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )

        mMap!!.isMyLocationEnabled = true
    }

    fun getCurrentAddress(latlng: LatLng): String {

        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address>?

        addresses = geocoder.getFromLocation(
            latlng.latitude,
            latlng.longitude,
            1
        )
        val address = addresses[0]
        return address.getAddressLine(0).toString()

    }

    fun setCurrentLocation(location: Location?, markerTitle: String, markerSnippet: String) {
        if (currentMarker != null) currentMarker!!.remove()

        val currentLatLng = LatLng(location!!.latitude, location.longitude)

        val markerOptions = MarkerOptions()
        markerOptions.position(currentLatLng)
        markerOptions.title(markerTitle)
        markerOptions.snippet(markerSnippet)
        markerOptions.draggable(true)

        currentMarker = mMap!!.addMarker(markerOptions)

        val cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng)
        mMap!!.moveCamera(cameraUpdate)

    }

    private fun setDefaultLocation() {
        val DEFAULT_LOCATION = LatLng(37.56, 126.97)
        val markerTitle = "위치정보 가져올 수 없음"
        val markerSnippet = "위치 퍼미션과 GPS 활성 요부 확인하세요"

        if (currentMarker != null) currentMarker!!.remove()

        val markerOptions = MarkerOptions()
        markerOptions.position(DEFAULT_LOCATION)
        markerOptions.title(markerTitle)
        markerOptions.snippet(markerSnippet)
        markerOptions.draggable(true)
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        currentMarker = mMap!!.addMarker(markerOptions)

        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15f)
        mMap!!.moveCamera(cameraUpdate)

    }

    companion object {
        private val UPDATE_INTERVAL_MS = 1000  // 1초
        private val FASTEST_UPDATE_INTERVAL_MS = 500 // 0.5초
    }
}