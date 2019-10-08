package com.yeonae.chamelezone

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeTabFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_tab_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()

        var placeList = arrayListOf(
            Place("backward", "구슬모아 당구장", "800m", "카페, 펍"),
            Place("course", "Chow Chow", "Male", "Male"),
            Place("home", "Chow Chow", "Male", "Male"),
            Place("like", "Chow Chow", "Male", "Male"),
            Place("search", "Chow Chow", "Male", "Male"),
            Place("map", "Chow Chow", "Male", "Male"),
            Place("user", "Chow Chow", "Male", "Male")
        )
        System.out.println(placeList.size)

        val gridlayout = GridLayoutManager(this.context, 2)
        val placeAdapter = this.context?.let { RecyclerViewAdapter(it, placeList) }

        val recyclerViewPlace = view?.findViewById<RecyclerView>(R.id.recycler_view_place)
        recyclerViewPlace?.apply {
            layoutManager = gridlayout
            adapter = placeAdapter
        }
    }
}