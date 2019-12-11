package com.yeonae.chamelezone.view.map

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
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.yeonae.chamelezone.App
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.view.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_map_tab.*
import java.util.*

class MapTabFragment : Fragment(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    lateinit var currentLocation: Location
    lateinit var currentLatLng: LatLng
    private lateinit var locationCallBack: LocationCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_map_tab, container, false)
    }

    override fun onMapReady(map: GoogleMap?) {
        if (map != null) {
            this.map = map
        }
        updateLocationUI()
        getCurrentLocation()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        map_view.onCreate(savedInstanceState)
        map_view.onResume()
        map_view.getMapAsync(this)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(App.instance.context())

        createLocationCallBack()
        createLocationRequest()

        btn_search.setOnClickListener {
            getSearchLocation()
        }
    }

    private fun createLocationCallBack() {
        locationCallBack = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                map?.clear()
                if (locationResult != null) {
                    currentLocation = locationResult.lastLocation
                }

                currentLatLng = LatLng(currentLocation?.latitude!!, currentLocation?.longitude!!)

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
            }
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
    }

    private fun updateLocationUI() {
        map?.isMyLocationEnabled = true
        map!!.uiSettings.isMyLocationButtonEnabled = true
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
            for (i in 0 until addresses.size) {
                val searchLatLng = LatLng(addresses[i].latitude, addresses[i].longitude)
                val markerTitle = getCurrentAddress(searchLatLng)
                val markerOptions = MarkerOptions().apply {
                    position(searchLatLng)
                    title(markerTitle)
                    draggable(true)
                }

                map?.run {
                    addMarker(markerOptions)
                    setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener {
                        override fun onMarkerClick(p0: Marker?): Boolean {
                            (activity as HomeActivity).back(MarkerInfoFragment())
                            (activity as HomeActivity).replace(MarkerInfoFragment(), true)
                            return false
                        }

                    })
                    animateCamera(CameraUpdateFactory.newLatLngZoom(searchLatLng, 15f))
                }
            }
        }
    }

    private fun getCurrentAddress(latLng: LatLng): String {
        val geoCoder = Geocoder(App.instance.context(), Locale.getDefault())
        val addresses = geoCoder.getFromLocation(
            latLng.latitude,
            latLng.longitude,
            1
        )
        return addresses[0].getAddressLine(0).toString()
    }
}