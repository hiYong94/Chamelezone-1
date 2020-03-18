package com.yeonae.chamelezone.view.map

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.view.map.adapter.MakerInfoRvAdapter
import com.yeonae.chamelezone.view.map.presenter.MarkerInfoContract
import com.yeonae.chamelezone.view.map.presenter.MarkerInfoPresenter
import com.yeonae.chamelezone.view.place.PlaceDetailActivity
import kotlinx.android.synthetic.main.fragment_marker_info.*


class MarkerInfoFragment : Fragment(), MarkerInfoContract.View {
    private val makerInfoRvAdapter = MakerInfoRvAdapter()
    override lateinit var presenter: MarkerInfoContract.Presenter

    override fun placeInfo(placeList: List<PlaceResponse>) {
        makerInfoRvAdapter.addData(placeList)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_marker_info, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = MarkerInfoPresenter(
            Injection.placeRepository(), this
        )
        setAdapter()
        arguments?.getString("searchWord")?.let { presenter.searchPlace(it) }
        val bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)

        makerInfoRvAdapter.setOnClickListener(object : MakerInfoRvAdapter.OnClickListener {
            override fun onClick(place: PlaceResponse) {
                val intent = Intent(requireContext(), PlaceDetailActivity::class.java)
                intent.putExtra(PLACE_NAME, place.name)
                intent.putExtra(PLACE_NUMBER, place.placeNumber)
                startActivity(intent)
            }
        })
    }

    private fun setAdapter() {
        recycler_marker_info.layoutManager = LinearLayoutManager(context)
        recycler_marker_info.adapter = makerInfoRvAdapter
    }

    companion object {
        private const val PLACE_NAME = "placeName"
        private const val PLACE_NUMBER = "placeNumber"
        fun newInstance(searchWord: String) = MarkerInfoFragment().apply {
            arguments = Bundle().apply {
                putString("searchWord", searchWord)
            }
        }
    }
}