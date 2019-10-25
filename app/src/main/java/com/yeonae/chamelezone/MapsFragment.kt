package com.yeonae.chamelezone

import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_maps.*
import noman.googleplaces.*
import java.util.*


class MapsFragment : Fragment(), OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback, PlacesListener {
    var previousMarker: List<Marker>? = null

    override fun onPlacesFailure(e: PlacesException?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPlacesSuccess(places: List<Place>?) {
        runOnUiThread(Runnable {
            if (places != null) {
                for (place in places) {
                    val latLng = LatLng(place.latitude, place.longitude)
                    val markerSnippet = getCurrentAddress(latLng)
                    val markerOptions = MarkerOptions()
                    markerOptions.position(latLng)
                    markerOptions.title(place.name)
                    markerOptions.snippet(markerSnippet)
                    val item = map?.addMarker(markerOptions)
                    previousMarker.add(item)
                }
            }
            val hashSet = HashSet<Marker>()
            hashSet.addAll(previousMarker!!)
            previousMarker.clear()
            previousMarker.addAll(hashSet)
        })
    }

    override fun onPlacesFinished() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPlacesStart() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var map: GoogleMap? = null
    private var currentMarker: Marker? = null
    var currentLocation: Location? = null
    lateinit var currentPosition: LatLng

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var location: Location? = null

    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            val locationList = locationResult?.locations

            if (locationList?.size!! > 0) {
                location = locationList[locationList.size - 1]

                currentPosition = LatLng(location!!.latitude, location!!.longitude)

                val markerTitle = getCurrentAddress(currentPosition)
                val markerSnippet = ("위도:" + location!!.latitude.toString()
                        + " 경도:" + location!!.longitude.toString())

                setCurrentLocation(location, markerTitle, markerSnippet)
                currentLocation = location
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

        map_view.run {
            onCreate(savedInstanceState)
            onResume()
            getMapAsync(this@MapsFragment)
        }

        locationRequest = LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY) //정확한 위치 요청
            .setInterval(UPDATE_INTERVAL_MS.toLong()) //활성 위치 업데이트 간격
            .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS.toLong()) //위치 업데이트 간격

        LocationSettingsRequest.Builder().addLocationRequest(locationRequest!!)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())


    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap

        setDefaultLocation()
        startLocationUpdates()

        map?.run {
            isMyLocationEnabled = true
            uiSettings.isMyLocationButtonEnabled = true
        }

        btn_search.setOnClickListener {
            searchLocation()
        }
    }

    private fun startLocationUpdates() {
        fusedLocationClient?.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

    fun getCurrentAddress(latLng: LatLng): String {
        val geoCoder = Geocoder(context, Locale.getDefault())
        val addresses = geoCoder.getFromLocation(
            latLng.latitude,
            latLng.longitude,
            1
        )
        return addresses[0].getAddressLine(0).toString()
    }

    fun setCurrentLocation(location: Location?, markerTitle: String, markerSnippet: String) {
        currentMarker?.remove()

        val currentLatLng = LatLng(location!!.latitude, location.longitude)

        val markerOptions = MarkerOptions().apply {
            position(currentLatLng)
            title(markerTitle)
            snippet(markerSnippet)
            draggable(true)
        }

        currentMarker = map?.addMarker(markerOptions)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))

    }

    private fun setDefaultLocation() {
        val defaultLocation = LatLng(37.56, 126.97)
        val markerTitle = "위치정보 가져올 수 없음"
        val markerSnippet = "위치 퍼미션과 GPS 활성 요부 확인하세요"

        currentMarker?.remove()

        val markerOptions = MarkerOptions().apply {
            position(defaultLocation)
            title(markerTitle)
            snippet(markerSnippet)
            draggable(true)
            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        }

        currentMarker = map?.addMarker(markerOptions)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15f))

    }

    private fun searchLocation() {
        map?.clear()
        val geoCoder = Geocoder(context, Locale.getDefault())
        val addresses = geoCoder.getFromLocationName(
            edt_search.text.toString(),
            1
        )
        val searchLatLng = LatLng(addresses[0].latitude, addresses[0].longitude)
        val markerTitle = getCurrentAddress(searchLatLng)

        val markerOptions = MarkerOptions().apply {
            position(searchLatLng)
            title(markerTitle)
            draggable(true)
        }

        map?.run {
            addMarker(markerOptions)
            animateCamera(CameraUpdateFactory.newLatLngZoom(searchLatLng, 15f))
        }

    }

    fun showPlaceInformation(location: LatLng) {
        map?.clear()//지도 클리어

        if (previous_marker != null)
            previous_marker.clear()//지역정보 마커 클리어

        NRPlaces.Builder()
            .listener(this)
            .key("Places API Web Service 키")
            .latlng(location.latitude, location.longitude)//현재 위치
            .radius(500) //500 미터 내에서 검색
            .type(PlaceType.RESTAURANT) //음식점
            .build()
            .execute()
    }

    companion object {
        private const val UPDATE_INTERVAL_MS = 1000  // 1초
        private const val FASTEST_UPDATE_INTERVAL_MS = 500 // 0.5초
    }
}