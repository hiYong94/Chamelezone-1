package com.yeonae.chamelezone.view.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.Place
import com.yeonae.chamelezone.view.home.adapter.HomePlaceRvAdapter
import com.yeonae.chamelezone.view.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_home_tab.*

class HomeTabFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_tab, container, false)
    }

    override fun onStart() {
        super.onStart()

        val placeList = arrayListOf(
            Place(
                "구슬모아당구장",
                "전시회, 카페",
                "서울 용산구 독서당로 85",
                "7km",
                "place1"
            ),
            Place(
                "론리드프로젝트",
                "빨래방, 카페",
                "서울 용산구 신흥로 78",
                "10km",
                "place2"
            ),
            Place(
                "하나은행X북바이북",
                "은행, 서점",
                "서울 종로구 새문안로5길 19",
                "13km",
                "place3"
            ),
            Place(
                "구슬모아당구장",
                "전시회, 카페",
                "서울 용산구 독서당로 85",
                "7km",
                "place1"
            ),
            Place(
                "론리드프로젝트",
                "빨래방, 카페",
                "서울 용산구 신흥로 78",
                "10km",
                "place2"
            ),
            Place(
                "하나은행X북바이북",
                "은행, 서점",
                "서울 종로구 새문안로5길 19",
                "13km",
                "place3"
            )
        )
        Log.d("tag", placeList.size.toString())

        val gridlayout = GridLayoutManager(context, 2)

        val placeAdapter = HomePlaceRvAdapter(placeList)

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
    }
}
