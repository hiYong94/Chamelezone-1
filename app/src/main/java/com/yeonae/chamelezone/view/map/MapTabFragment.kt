package com.yeonae.chamelezone.view.map

import android.Manifest
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Rect
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
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
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.SingleDialogFragment
import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.view.home.HomeActivity
import com.yeonae.chamelezone.view.map.presenter.MapContract
import com.yeonae.chamelezone.view.map.presenter.MapPresenter
import kotlinx.android.synthetic.main.fragment_map_tab.*

class MapTabFragment : Fragment(), OnMapReadyCallback, MapContract.View,
    GoogleMap.OnMarkerClickListener {
    override lateinit var presenter: MapContract.Presenter
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var currentLocation: Location
    private lateinit var currentLatLng: LatLng
    private lateinit var locationCallBack: LocationCallback

    override fun showMessage(message: String) {
        layout_no_search.visibility = View.VISIBLE
        map_fragment.visibility = View.GONE
        tv_message.text = message
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        (activity as? HomeActivity)?.back()
        if (marker != null) {
            (activity as? HomeActivity)?.replace(
                SingleInfoFragment.newInstance(
                    "${edt_search.text}".trim()
                ), true
            )
        }
        return false
    }

    override fun placeInfo(placeList: List<PlaceResponse>) {
        layout_no_search.visibility = View.GONE
        map_fragment.visibility = View.VISIBLE
        map.clear()
        for (i in placeList.indices) {
            val searchLatLng =
                LatLng(placeList[i].latitude.toDouble(), placeList[i].longitude.toDouble())
            val markerOptions = MarkerOptions().apply {
                position(searchLatLng)
                title(placeList[i].name)
                draggable(true)
            }
            if (placeList.size == 1) {
                (activity as? HomeActivity)?.back()
                map.addMarker(markerOptions).showInfoWindow()
                (activity as? HomeActivity)?.replace(
                    SingleInfoFragment.newInstance(
                        "${edt_search.text}".trim()
                    ), true
                )
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(searchLatLng, 15f))

            } else {
                (activity as? HomeActivity)?.back()
                (activity as? HomeActivity)?.replace(
                    MarkerInfoFragment.newInstance(
                        "${edt_search.text}".trim()
                    ), true
                )

                map.run {
                    addMarker(markerOptions)
                    setOnMarkerClickListener(this@MapTabFragment)
                    animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                placeList[0].latitude.toDouble(),
                                placeList[0].longitude.toDouble()
                            ), 9f
                        )
                    )
                }
            }
        }
    }

    private val permissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            loadMap()
            val prefs: SharedPreferences =
                requireActivity().getSharedPreferences("Pref", MODE_PRIVATE)
            val isFirstRun = prefs.getBoolean("isFirstRun", true)
            if (isFirstRun) {
                Toast.makeText(activity, R.string.permission_granted, Toast.LENGTH_SHORT).show()
                prefs.edit().putBoolean("isFirstRun", false).apply()
            }
        }

        override fun onPermissionDenied(deniedPermissions: List<String>) {
            Toast.makeText(
                activity,
                R.string.permission_denied,
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
        val locationButton =
            (map_view.findViewById<View>(Integer.parseInt("1")).parent as View).findViewById<View>(
                Integer.parseInt("2")
            )
        val layoutParams = locationButton.layoutParams as RelativeLayout.LayoutParams
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        layoutParams.setMargins(0, 0, 30, 30)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        map_view.onCreate(savedInstanceState)

        presenter = MapPresenter(
            Injection.placeRepository(), this
        )

        checkPermission()



        edt_search.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_GO) {
                if ("${edt_search.text}".isEmpty()) {
                    showDialog()
                } else {
                    val imm =
                        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(edt_search.windowToken, 0)
                    val searchWord = "${edt_search.text}".replace(" ", "")
                    presenter.searchPlace(searchWord)
                }
            }
            true
        }

        btn_search.setOnClickListener {
            if ("${edt_search.text}".isEmpty()) {
                showDialog()
            } else {
                val imm =
                    context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(edt_search.windowToken, 0)
                val searchWord = "${edt_search.text}".replace(" ", "")
                presenter.searchPlace(searchWord)
            }
        }

        keyBoard()

    }

    private fun checkPermission() {
        TedPermission.with(activity)
            .setPermissionListener(permissionListener)
            .setRationaleTitle(R.string.rationale_title)
            .setRationaleMessage(R.string.rationale_message)
            .setDeniedTitle(R.string.Permission_denied)
            .setDeniedMessage(R.string.permission_msg)
            .setGotoSettingButtonText(R.string.setting)
            .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
            .check()
    }

    private fun loadMap() {
        map_view.onResume()
        map_view.getMapAsync(this)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(App.instance.context())

        createLocationCallBack()
        createLocationRequest()
    }


    private fun createLocationCallBack() {
        locationCallBack = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                map.clear()
                if (locationResult != null) {
                    currentLocation = locationResult.lastLocation
                }

                currentLatLng = LatLng(currentLocation.latitude, currentLocation.longitude)

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
            }
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
    }

    private fun updateLocationUI() {
        map.isMyLocationEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true
    }

    private fun getCurrentLocation() {
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallBack,
            Looper.myLooper()
        )
    }

    private fun keyBoard() {
        var isKeyboardShowing = false
        contentView.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            try {
                contentView.getWindowVisibleDisplayFrame(r)
                val screenHeight = contentView.rootView.height
                val keypadHeight = screenHeight - r.bottom
                if (keypadHeight > screenHeight * 0.15) {
                    if (!isKeyboardShowing) {
                        isKeyboardShowing = true
                        (activity as HomeActivity).tabGone()
                    }
                } else {
                    if (isKeyboardShowing) {
                        isKeyboardShowing = false
                        (activity as HomeActivity).tabVisible()
                    }
                }
            } catch (e: Exception) {
                Log.d("MapTabFragment", "$e")
            }
        }
    }

    private fun showDialog() {
        val newFragment = SingleDialogFragment.newInstance(
            R.string.enter_search
        )
        fragmentManager?.let {
            newFragment.show(it, "dialog")
        }
    }
}