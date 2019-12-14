package com.yeonae.chamelezone.view.map

import android.Manifest
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.yeonae.chamelezone.App
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.view.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_map_tab.*
import java.util.*


class MapTabFragment : Fragment(), OnMapReadyCallback {
    private var markerInfoFragment = MarkerInfoFragment()
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    lateinit var currentLocation: Location
    lateinit var currentLatLng: LatLng
    private lateinit var locationCallBack: LocationCallback

    val permissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            loadMap()
            Toast.makeText(activity, "Permission Granted", Toast.LENGTH_SHORT).show()
        }

        override fun onPermissionDenied(deniedPermissions: List<String>) {
            Toast.makeText(
                activity,
                "Permission Denied\n$deniedPermissions",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }


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
        getCurrentLocation()
        updateLocationUI()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        map_view.onCreate(savedInstanceState)

        checkPermission()

        btn_search.setOnClickListener {
            getSearchLocation()
        }

    }

    private fun checkPermission(){
        TedPermission.with(activity)
            .setPermissionListener(permissionListener)
            .setRationaleTitle(R.string.rationale_title)
            .setRationaleMessage(R.string.rationale_message)
            .setDeniedTitle("Permission denied")
            .setDeniedMessage(
                "If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]"
            )
            .setGotoSettingButtonText("bla bla")
            .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
            .check()
    }
    private fun loadMap(){

        map_view.onResume()
        map_view.getMapAsync(this)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(App.instance.context())

        createLocationCallBack()
        createLocationRequest()
    }

    override fun onResume() {
        super.onResume()
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
                            (activity as? HomeActivity)?.back(markerInfoFragment)
                            (activity as? HomeActivity)?.replace(markerInfoFragment, true)
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