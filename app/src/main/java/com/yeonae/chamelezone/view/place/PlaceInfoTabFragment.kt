package com.yeonae.chamelezone.view.place

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.view.place.presenter.PlaceInfoContract
import com.yeonae.chamelezone.view.place.presenter.PlaceInfoPresenter
import kotlinx.android.synthetic.main.fragment_place_info_tab.*

class PlaceInfoTabFragment(val placeNumber: Int) : Fragment(), PlaceInfoContract.View {
    override fun placeInfo(place: PlaceResponse) {
        tv_keyword.text = place.keywordName
        tv_address.text = place.address
        tv_phone.text = place.phoneNumber
        tv_opening_time.text = place.openingTime
        tv_content.text = place.content
    }

    override lateinit var presenter: PlaceInfoContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_place_info_tab, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = PlaceInfoPresenter(
            Injection.placeRepository(requireContext()), this
        )
        Log.d("placeNumber", placeNumber.toString())
        presenter.placeDetail(placeNumber)
    }
}