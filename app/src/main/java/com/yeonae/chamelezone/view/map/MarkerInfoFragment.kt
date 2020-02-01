package com.yeonae.chamelezone.view.map

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.view.place.PlaceDetailActivity
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
        arguments?.run {
            tv_place_name.text = getString(PLACE_NAME)
            tv_place_keyword.text = getString(PLACE_KEYWORD)
            tv_place_address.text = getString(PLACE_ADDRESS)
        }
        layout_info.setOnClickListener {
            val intent = Intent(requireContext(), PlaceDetailActivity::class.java)
            intent.putExtra(PLACE_NAME, arguments?.getString(PLACE_NAME))
            intent.putExtra(PLACE_NUMBER, arguments?.getInt(PLACE_NUMBER))
            startActivity(intent)
        }
    }

    companion object {
        private const val PLACE_NAME = "placeName"
        private const val PLACE_KEYWORD = "placeKeyword"
        private const val PLACE_ADDRESS = "placeAddress"
        private const val PLACE_NUMBER = "placeNumber"
        fun newInstance(
            placeInfo: PlaceResponse
        ) = MarkerInfoFragment().apply {
            arguments = Bundle().apply {
                putString(PLACE_NAME, placeInfo.name)
                putString(PLACE_KEYWORD, placeInfo.keywordName)
                putString(PLACE_ADDRESS, placeInfo.address)
                putInt(PLACE_NUMBER, placeInfo.placeNumber)
            }
        }
    }
}