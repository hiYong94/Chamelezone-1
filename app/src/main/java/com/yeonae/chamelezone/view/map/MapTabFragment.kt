package com.yeonae.chamelezone.view.map

import androidx.fragment.app.Fragment

class MapTabFragment : Fragment()/*, OnMapReadyCallback */{
//    private lateinit var map: GoogleMap
//    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
//    private lateinit var locationRequest: LocationRequest
//    lateinit var currentLocation: Location
//    lateinit var currentLatLng: LatLng
//    private lateinit var locationCallBack: LocationCallback
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        return inflater.inflate(R.layout.fragment_map_tab, container, false)
//    }
//
//    override fun onMapReady(map: GoogleMap?) {
//        if (map != null) {
//            this.map = map
//        }
//        updateLocationUI()
//        getCurrentLocation()
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        map_view.onCreate(savedInstanceState)
//        map_view.onResume()
//        map_view.getMapAsync(this)
//
//        fusedLocationProviderClient =
//            LocationServices.getFusedLocationProviderClient(App.instance.context())
//
//        createLocationCallBack()
//        createLocationRequest()
//
//        btn_search.setOnClickListener {
//            getSearchLocation()
//        }
//    }
//
//    private fun createLocationCallBack() {
//        locationCallBack = object : LocationCallback() {
//            override fun onLocationResult(locationResult: LocationResult?) {
//                super.onLocationResult(locationResult)
//                map?.clear()
//                if (locationResult != null) {
//                    currentLocation = locationResult.lastLocation
//                }
//
//                currentLatLng = LatLng(currentLocation?.latitude!!, currentLocation?.longitude!!)
//                val markerTitle = getCurrentAddress(currentLatLng!!)
//                val markerOptions = MarkerOptions().apply {
//                    position(currentLatLng!!)
//                    title(markerTitle)
//                    draggable(true)
//                }
//
//                map?.run {
//                    addMarker(markerOptions)
//                    moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
//                }
//            }
//        }
//    }
//
//    private fun createLocationRequest() {
//        locationRequest = LocationRequest()
//            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//    }
//
//    private fun updateLocationUI() {
//        map?.isMyLocationEnabled = true
//        map!!.uiSettings.isMyLocationButtonEnabled = true
//    }
//
//    private fun getCurrentLocation() {
//        fusedLocationProviderClient?.requestLocationUpdates(
//            locationRequest,
//            locationCallBack,
//            Looper.myLooper()
//        )
//    }
//
//    private fun getSearchLocation() {
//        map?.clear()
//        val geoCoder = Geocoder(App.instance.context(), Locale.getDefault())
//        val addresses = geoCoder.getFromLocationName(
//            edt_search.text.toString(),
//            10
//        )
//
//        if (addresses != null) {
//            for (i in 0 until addresses.size) {
//                val searchLatLng = LatLng(addresses[i].latitude, addresses[i].longitude)
//                val markerTitle = getCurrentAddress(searchLatLng)
//                val markerOptions = MarkerOptions().apply {
//                    position(searchLatLng)
//                    title(markerTitle)
//                    draggable(true)
//                }
//
//                map?.run {
//                    addMarker(markerOptions)
//                    animateCamera(CameraUpdateFactory.newLatLngZoom(searchLatLng, 15f))
//                }
//            }
//        }
//    }
//
//    fun getCurrentAddress(latLng: LatLng): String {
//        val geoCoder = Geocoder(App.instance.context(), Locale.getDefault())
//        val addresses = geoCoder.getFromLocation(
//            latLng.latitude,
//            latLng.longitude,
//            1
//        )
//        return addresses[0].getAddressLine(0).toString()
//    }
}