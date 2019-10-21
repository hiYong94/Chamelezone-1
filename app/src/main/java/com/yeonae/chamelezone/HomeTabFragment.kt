package com.yeonae.chamelezone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.provider.FontsContractCompat.Columns.RESULT_CODE
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.home_tab_fragment.*
import kotlinx.android.synthetic.main.place_list_item.*

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

        val placeList = arrayListOf(
            Place("backward", "구슬모아 당구장", "800m", "카페, 펍"),
            Place("course", "Chow Chow", "Male", "Male"),
            Place("home", "Chow Chow", "Male", "Male"),
            Place("like", "Chow Chow", "Male", "Male"),
            Place("search", "Chow Chow", "Male", "Male"),
            Place("map", "Chow Chow", "Male", "Male"),
            Place("user", "Chow Chow", "Male", "Male")
        )
        Log.d("tag", placeList.size.toString())

        val gridlayout = GridLayoutManager(context, 2)
        val placeAdapter = RecyclerViewAdapter(placeList)

        recycler_view_place?.apply {
            layoutManager = gridlayout
            adapter = placeAdapter

        }
    }
}
