package com.yeonae.chamelezone.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.view.home.adapter.HomePlaceRvAdapter
import com.yeonae.chamelezone.data.model.Place
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
                "backward",
                "구슬모아 당구장",
                "800m",
                "카페, 펍"
            ),
            Place(
                "course",
                "Chow Chow",
                "Male",
                "Male"
            ),
            Place(
                "home",
                "Chow Chow",
                "Male",
                "Male"
            ),
            Place(
                "like",
                "Chow Chow",
                "Male",
                "Male"
            ),
            Place(
                "search",
                "Chow Chow",
                "Male",
                "Male"
            ),
            Place(
                "map",
                "Chow Chow",
                "Male",
                "Male"
            ),
            Place(
                "user",
                "Chow Chow",
                "Male",
                "Male"
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
}