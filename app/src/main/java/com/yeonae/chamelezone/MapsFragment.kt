package com.yeonae.chamelezone

import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_maps.*
import java.util.*

class MapsFragment : Fragment(), OnMapReadyCallback,
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        map.onCreate(savedInstanceState)
        map.onResume()
        map.getMapAsync(this)

        locationRequest = LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL_MS.toLong())
            .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS.toLong())

        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest!!)

        mFusedLocationClient = context?.let { LocationServices.getFusedLocationProviderClient(it) }

        btn_search.setOnClickListener {
            mMap!!.clear()
            val geocoder = Geocoder(context, Locale.getDefault())
            val str = edt_search.text.toString()
            val addresses: List<Address>?
            addresses = geocoder.getFromLocationName(
                str,
                1
            )
            val latitude = addresses[0].latitude
            val longitude = addresses[0].longitude

            val place = LatLng(latitude, longitude)

            val markerOptions = MarkerOptions().apply {
                position(place)
                title(str)
            }
            mMap!!.addMarker(markerOptions)

            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(place))
            mMap!!.animateCamera(CameraUpdateFactory.zoomTo(15f))

        }

    }

    override fun onMapReady(googleMap: GoogleMap?) {
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

    fun getCurrentAddress(latLng: LatLng): String {

        val geoCoder = Geocoder(context, Locale.getDefault())
        val addresses: List<Address>?

        addresses = geoCoder.getFromLocation(
            latLng.latitude,
            latLng.longitude,
            1
        )
        val address = addresses[0]
        return address.getAddressLine(0).toString()

    }

    fun setCurrentLocation(location: Location?, markerTitle: String, markerSnippet: String) {
        if (currentMarker != null) currentMarker!!.remove()

        val currentLatLng = LatLng(location!!.latitude, location.longitude)

        val markerOptions = MarkerOptions().apply {
            position(currentLatLng)
            title(markerTitle)
            snippet(markerSnippet)
            draggable(true)
        }

        currentMarker = mMap!!.addMarker(markerOptions)

        val cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng)
        mMap!!.moveCamera(cameraUpdate)

    }

    private fun setDefaultLocation() {
        val defaultLocation = LatLng(37.56, 126.97)
        val markerTitle = "위치정보 가져올 수 없음"
        val markerSnippet = "위치 퍼미션과 GPS 활성 요부 확인하세요"

        if (currentMarker != null) currentMarker!!.remove()

        val markerOptions = MarkerOptions().apply {
            position(defaultLocation)
            title(markerTitle)
            snippet(markerSnippet)
            draggable(true)
            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        }
        currentMarker = mMap!!.addMarker(markerOptions)

        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(defaultLocation, 15f)
        mMap!!.moveCamera(cameraUpdate)

    }

    companion object {
        private const val UPDATE_INTERVAL_MS = 1000  // 1초
        private const val FASTEST_UPDATE_INTERVAL_MS = 500 // 0.5초
    }
}