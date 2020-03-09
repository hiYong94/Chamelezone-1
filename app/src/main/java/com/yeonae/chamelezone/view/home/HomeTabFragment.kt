package com.yeonae.chamelezone.view.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity
import com.yeonae.chamelezone.view.home.adapter.HomePlaceRvAdapter
import com.yeonae.chamelezone.view.home.presenter.HomeContract
import com.yeonae.chamelezone.view.home.presenter.HomePresenter
import com.yeonae.chamelezone.view.place.PlaceDetailActivity
import com.yeonae.chamelezone.view.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_home_tab.*

class HomeTabFragment : Fragment(), HomeContract.View {
    override lateinit var presenter: HomeContract.Presenter
    private val placeAdapter = HomePlaceRvAdapter()
    var memberNumber: Int? = null

    override fun showHomeList(response: List<PlaceResponse>) {
        placeAdapter.addData(response)
    }

    override fun getMember(user: UserEntity) {
        memberNumber = user.userNumber
        Log.d("HomeTabFragment memberNumber", memberNumber.toString())
        presenter.getHomeList(memberNumber)
        Log.d("HomeTabFragment memberNumber2", memberNumber.toString())
    }

    override fun getMemberCheck(response: Boolean) {
        presenter.getMember()
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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_search.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }
        presenter = HomePresenter(
            Injection.placeRepository(), Injection.memberRepository(requireContext()), this
        )
        presenter.checkMember()

        placeAdapter.setItemClickListener(object : HomePlaceRvAdapter.OnItemClickListener {
            override fun onItemClick(place: PlaceResponse) {
                val intent = Intent(requireContext(), PlaceDetailActivity::class.java)
                intent.putExtra(PLACE_NAME, place.name)
                intent.putExtra(PLACE_NUMBER, place.placeNumber)
                Log.d("HomeTabFragment placeDetail name2", place.name)
                Log.d("HomeTabFragment placeDetail number2", place.placeNumber.toString())
                startActivity(intent)
            }
        })
    }

    companion object {
        private const val PLACE_NAME = "placeName"
        private const val PLACE_NUMBER = "placeNumber"
    }
}