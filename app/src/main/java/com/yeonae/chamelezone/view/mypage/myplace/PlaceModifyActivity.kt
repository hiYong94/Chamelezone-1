package com.yeonae.chamelezone.view.mypage.myplace

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.LatLng
import com.kroegerama.imgpicker.BottomSheetImagePicker
import com.yeonae.chamelezone.R
import kotlinx.android.synthetic.main.fragment_place_modify.*
import java.io.IOException

class PlaceModifyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_place_modify, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private fun findLatLng(context: Context, address: String): LatLng? {
        val coder = Geocoder(context)
        var addresses: List<Address>? = null
        var latLng: LatLng? = null

        try {
            addresses = coder.getFromLocationName(address, 5)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (addresses != null) {
            for (i in addresses.indices) {
                latLng = LatLng(addresses[i].latitude, addresses[i].longitude)
            }
        }
        return latLng
    }

    private fun pickMulti() {
        fragmentManager?.let {
            BottomSheetImagePicker.Builder(getString(R.string.file_provider))
                .multiSelect(2, 4)
                .multiSelectTitles(
                    R.plurals.pick_multi,
                    R.plurals.pick_multi_more,
                    R.string.pick_multi_limit
                )
                .peekHeight(R.dimen.peekHeight)
                .columnSize(R.dimen.columnSize)
                .requestTag("사진이 선택되었습니다.")
                .show(it)
        }
    }

    private fun setupGUI() {
        btn_image_create.setOnClickListener { pickMulti() }
        btn_image_clear.setOnClickListener { imageContainer.removeAllViews() }
    }
}