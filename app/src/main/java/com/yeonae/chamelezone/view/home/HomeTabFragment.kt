package com.yeonae.chamelezone.view.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.LikeStatusItem
import com.yeonae.chamelezone.ext.shortToast
import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity
import com.yeonae.chamelezone.view.home.adapter.HomePlaceRvAdapter
import com.yeonae.chamelezone.view.home.presenter.HomeContract
import com.yeonae.chamelezone.view.home.presenter.HomePresenter
import com.yeonae.chamelezone.view.login.LoginActivity
import com.yeonae.chamelezone.view.place.PlaceDetailActivity
import com.yeonae.chamelezone.view.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_home_tab.*

class HomeTabFragment :
    Fragment(),
    SwipeRefreshLayout.OnRefreshListener,
    HomeContract.View {
    override lateinit var presenter: HomeContract.Presenter
    private lateinit var placeAdapter: HomePlaceRvAdapter
    private var memberNumber: Int = 0
    private var placeNumber = 0
    private var currentLongitude: Double = 0.0
    private var currentLatitude: Double = 0.0

    override fun showHomeList(placeList: List<PlaceResponse>) {
        if (::placeAdapter.isInitialized) {
            placeAdapter.addData(placeList)
        }
    }

    override fun getMember(user: UserEntity) {
        if (user.userNumber != null)
            memberNumber = user.userNumber
        presenter.getHomeList(memberNumber)
    }

    override fun getMemberCheck(response: Boolean) {

        if (response) {
            presenter.getMember()
        } else {
            memberNumber = 0
            presenter.getHomeList(memberNumber)
        }
    }

    override fun showLikeMessage(response: LikeStatusItem) {
        if (response.likeStatus) {
            context?.shortToast(R.string.select_like)
        }
    }

    override fun showDeleteLikeMessage(response: LikeStatusItem) {
        if (!response.likeStatus) {
            context?.shortToast(R.string.delete_like)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_layout.setOnRefreshListener(this)
        swipe_layout.setColorSchemeResources(R.color.colorOrange)

        btn_search.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }

        presenter = HomePresenter(
            Injection.placeRepository(),
            Injection.memberRepository(),
            Injection.likeRepository(),
            this
        )

        placeAdapter = HomePlaceRvAdapter()

        placeAdapter.setItemClickListener(object :
            HomePlaceRvAdapter.OnItemClickListener {
            override fun onItemClick(
                view: View,
                position: Int,
                place: PlaceResponse
            ) {
                placeNumber = place.placeNumber
                val intent =
                    Intent(requireContext(), PlaceDetailActivity::class.java)
                intent.putExtra(PLACE_NAME, place.name)
                intent.putExtra(PLACE_NUMBER, place.placeNumber)
                startActivity(intent)
            }
        })

        placeAdapter.setLikeButtonListener(object :
            HomePlaceRvAdapter.LikeButtonListener {
            override fun onLikeClick(
                placeResponse: PlaceResponse,
                isChecked: Boolean
            ) {
                placeNumber = placeResponse.placeNumber

                if (memberNumber == 0) {
                    val intent = Intent(context, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    if (isChecked) {
                        presenter.selectLike(memberNumber, placeNumber)
                    } else {
                        presenter.deleteLike(memberNumber, placeNumber)
                    }
                }
            }
        })

        recycler_view_place.run {
            layoutManager = GridLayoutManager(context, 2)
            if (::placeAdapter.isInitialized)
                adapter = placeAdapter
        }

        calculatorLocation()
    }

    override fun onResume() {
        super.onResume()
        if (::presenter.isInitialized)
            presenter.checkMember()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N &&
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentLocation()
        }
    }

    override fun onRefresh() {
        presenter.getHomeList(memberNumber)
        swipe_layout.isRefreshing = false

        if (::presenter.isInitialized)
            presenter.checkMember()
    }

    private fun calculatorLocation() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N &&
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            context?.shortToast(R.string.location_request)
        } else {
            getCurrentLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        val lm =
            context?.getSystemService(LOCATION_SERVICE) as? LocationManager
        var location: Location? =
            lm?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location == null) {
            location = lm?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        }
        if (location != null) {
            currentLatitude = location.latitude
            currentLongitude = location.longitude
            placeAdapter.replaceDataDistance(currentLatitude, currentLongitude)
        }
    }

    companion object {
        private const val PLACE_NAME = "placeName"
        private const val PLACE_NUMBER = "placeNumber"
    }
}