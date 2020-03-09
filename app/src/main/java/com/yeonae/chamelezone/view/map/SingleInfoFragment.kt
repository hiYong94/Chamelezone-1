package com.yeonae.chamelezone.view.map

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.Url.IMAGE_RESOURCE
import com.yeonae.chamelezone.ext.glideImageSet
import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.view.map.presenter.MapContract
import com.yeonae.chamelezone.view.map.presenter.MapPresenter
import com.yeonae.chamelezone.view.place.PlaceDetailActivity
import kotlinx.android.synthetic.main.fragment_single_info.*

class SingleInfoFragment : Fragment(), MapContract.View {
    override lateinit var presenter: MapContract.Presenter
    var placeNumber: Int = 0
    var placeName: String = ""

    override fun placeInfo(placeList: List<PlaceResponse>) {
        placeName = placeList[0].name
        Log.d("placeListName", placeList[0].name)
        tv_place_name.text = placeName
        placeNumber = placeList[0].placeNumber
        placeList[0].keywordName.forEach {
            if (it == placeList[0].keywordName[0]) {
                tv_place_keyword.text = it
            } else {
                tv_place_keyword.text = "${tv_place_keyword.text}${","} $it"
            }
        }
        tv_place_address.text = placeList[0].address
        val image = IMAGE_RESOURCE + placeList[0].savedImageName[0]
        iv_place_image.glideImageSet(
            image, iv_place_image.measuredWidth,
            iv_place_image.measuredHeight
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_single_info, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = MapPresenter(
            Injection.placeRepository(), this
        )
        arguments?.getString("searchWord")?.let { presenter.searchPlace(it) }
        Log.d("searchWord", arguments?.getString("searchWord"))

        layout_info.setOnClickListener {
            val intent = Intent(requireContext(), PlaceDetailActivity::class.java)
            intent.putExtra(PLACE_NAME, placeName)
            intent.putExtra(PLACE_NUMBER, placeNumber)
            startActivity(intent)
        }
    }

    companion object {
        private const val PLACE_NAME = "placeName"
        private const val PLACE_NUMBER = "placeNumber"
        fun newInstance(searchWord: String) = SingleInfoFragment().apply {
            arguments = Bundle().apply {
                putString("searchWord", searchWord)
            }
        }
    }
}