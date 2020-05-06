package tk.yeonaeyong.shopinshop.view.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_place_info_tab.*
import tk.yeonaeyong.shopinshop.Injection
import tk.yeonaeyong.shopinshop.R
import tk.yeonaeyong.shopinshop.data.model.PlaceInfoItem
import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity
import tk.yeonaeyong.shopinshop.view.place.presenter.PlaceInfoContract
import tk.yeonaeyong.shopinshop.view.place.presenter.PlaceInfoPresenter

class PlaceInfoTabFragment : Fragment(), PlaceInfoContract.View, OnMapReadyCallback {
    override lateinit var presenter: PlaceInfoContract.Presenter
    private lateinit var map: GoogleMap
    var placeNumber: Int = 0

    override fun getPlaceDetail() {
        presenter.placeDetail(placeNumber, null)
    }

    override fun showUserInfo(user: UserEntity) {
        user.userNumber?.let { presenter.placeDetail(placeNumber, it) }
    }

    override fun placeInfo(place: PlaceInfoItem) {
        tv_keyword.text = place.keyword
        tv_address.text = place.address
        tv_phone.text = place.phoneNumber
        tv_opening_time.text = place.openingTime
        tv_content.text = place.content

        val latLng = LatLng(place.latitude.toDouble(), place.longitude.toDouble())
        val markerOptions = MarkerOptions().apply {
            position(latLng)
            title(place.name)
            draggable(true)
        }

        map.run {
            addMarker(markerOptions)
            moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            uiSettings.setAllGesturesEnabled(false)
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        if (map != null) {
            this.map = map
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_place_info_tab, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        place_info_map.onCreate(savedInstanceState)
        place_info_map.onResume()
        place_info_map.getMapAsync(this)

        presenter = PlaceInfoPresenter(
            Injection.placeRepository(), Injection.memberRepository(), this
        )
        placeNumber = arguments?.getInt(PLACE_NUMBER) ?: 0
        presenter.getUser()
    }

    companion object {
        private const val PLACE_NUMBER = "placeNumber"
        fun newInstance(placeNumber: Int) = PlaceInfoTabFragment().apply {
            arguments = Bundle().apply {
                putInt(PLACE_NUMBER, placeNumber)
            }
        }
    }
}