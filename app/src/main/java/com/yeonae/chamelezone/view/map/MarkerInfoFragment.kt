package com.yeonae.chamelezone.view.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.network.model.PlaceResponse
import kotlinx.android.synthetic.main.fragment_marker_info.*

class MarkerInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_marker_info, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_place_name.text = arguments!!.getString("placeName")
        tv_place_keyword.text = arguments!!.getString("placeKeyword")
        tv_place_address.text = arguments!!.getString("placeAddress")

    }

    companion object {
        fun newInstance(
            placeInfo: PlaceResponse
        ) = MarkerInfoFragment().apply {
            arguments = Bundle().apply {
                putString("placeName", placeInfo.name)
                putString("placeKeyword", placeInfo.keywordName)
                putString("placeAddress", placeInfo.address)
            }

        }
    }
}