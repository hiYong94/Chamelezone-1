package com.yeonae.chamelezone.view.home

import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.gun0912.tedpermission.PermissionListener
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

class HomeTabFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener, HomeContract.View {
    override lateinit var presenter: HomeContract.Presenter
    private val placeAdapter = HomePlaceRvAdapter()
    private var memberNumber: Int? = 0
    private var placeNumber = 0

    override fun showHomeList(place: List<PlaceResponse>) {
        placeAdapter.addData(place)

        placeAdapter.setLikeButtonListener(object : HomePlaceRvAdapter.LikeButtonListener {
            override fun onLikeClick(placeResponse: PlaceResponse, isChecked: Boolean) {
                placeNumber = placeResponse.placeNumber

                if (memberNumber == null) {
                    val intent = Intent(context, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    if (isChecked) {
                        memberNumber?.let { it1 -> presenter.selectLike(it1, placeNumber) }
                    } else {
                        memberNumber?.let { it1 ->
                            presenter.deleteLike(
                                it1,
                                placeNumber
                            )
                        }
                    }
                }
            }
        })
    }

    override fun getMember(user: UserEntity) {
        memberNumber = user.userNumber
        presenter.getHomeList(memberNumber)
    }

    override fun getMemberCheck(response: Boolean) {
        if (response) {
            presenter.getMember()
        } else {
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

    override fun onStart() {
        super.onStart()

        val gridlayout = GridLayoutManager(context, 2)

        recycler_view_place?.apply {
            layoutManager = gridlayout
            adapter = placeAdapter
        }
        presenter.checkMember()
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
            Injection.placeRepository(), Injection.memberRepository(),
            Injection.likeRepository(),
            this
        )

        placeAdapter.setItemClickListener(object : HomePlaceRvAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int, place: PlaceResponse) {
                placeNumber = place.placeNumber
                val intent = Intent(requireContext(), PlaceDetailActivity::class.java)
                intent.putExtra(PLACE_NAME, place.name)
                intent.putExtra(PLACE_NUMBER, place.placeNumber)
                startActivity(intent)
            }
        })
    }

    override fun onRefresh() {
        presenter.getHomeList(memberNumber)
        swipe_layout.isRefreshing = false
    }

    companion object {
        private const val PLACE_NAME = "placeName"
        private const val PLACE_NUMBER = "placeNumber"
    }


}