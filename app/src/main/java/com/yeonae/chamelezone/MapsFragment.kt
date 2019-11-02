package com.yeonae.chamelezone

import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_maps.*
import java.util.*

class MapsFragment : Fragment(), OnMapReadyCallback {
    private var map: GoogleMap? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    var currentLocation: Location? = null
    var currentLatLng: LatLng? = null

    private var locationCallBack: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            map?.clear()
            if (locationResult != null) {
                currentLocation = locationResult.lastLocation
            }

            currentLatLng = LatLng(currentLocation?.latitude!!, currentLocation?.longitude!!)
            val markerTitle = getCurrentAddress(currentLatLng!!)
            val markerOptions = MarkerOptions().apply {
                position(currentLatLng!!)
                title(markerTitle)
                draggable(true)
            }

            map?.run {
                addMarker(markerOptions)
                moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
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
        map_view.onCreate(savedInstanceState)
        map_view.onResume()
        map_view.getMapAsync(this)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(App.instance.context())

        locationCallBack
        createLocationRequest()

        btn_search.setOnClickListener {
            getSearchLocation()
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        this.map = map
        updateLocationUI()
        getCurrentLocation()
    }

    private fun getCurrentLocation() {
        fusedLocationProviderClient?.requestLocationUpdates(
            locationRequest,
            locationCallBack,
            Looper.myLooper()
        )
    }

    private fun getSearchLocation() {
        map?.clear()
        val geoCoder = Geocoder(App.instance.context(), Locale.getDefault())
        val addresses = geoCoder.getFromLocationName(
            edt_search.text.toString(),
            10
        )

        if (addresses != null) {
            for(i in 0..addresses.size){
                val searchLatLng = LatLng(addresses[i].latitude, addresses[i].longitude)
                //val markerTitle = getCurrentAddress(searchLatLng)
                val markerOptions = MarkerOptions().apply {
                    position(searchLatLng)
                    title(edt_search.text.toString())
                    draggable(true)
                }

                map?.run {
                    addMarker(markerOptions)
                    animateCamera(CameraUpdateFactory.newLatLngZoom(searchLatLng, 15f))
                }
            }
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//            .setInterval(UPDATE_INTERVAL_MS.toLong())
//            .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS.toLong())
    }

    private fun updateLocationUI() {
        map?.isMyLocationEnabled = true
        map!!.uiSettings.isMyLocationButtonEnabled = true
    }

    fun getCurrentAddress(latLng: LatLng): String {
        val geoCoder = Geocoder(App.instance.context(), Locale.getDefault())
        val addresses = geoCoder.getFromLocation(
            latLng.latitude,
            latLng.longitude,
            1
        )
        return addresses[0].getAddressLine(0).toString()
    }

    companion object {
        private const val UPDATE_INTERVAL_MS = 1000  // 1초
        private const val FASTEST_UPDATE_INTERVAL_MS = 500 // 0.5초
    }
}