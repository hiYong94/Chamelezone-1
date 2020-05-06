package tk.yeonaeyong.shopinshop.view.map

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.fragment_marker_info.*
import tk.yeonaeyong.shopinshop.Injection
import tk.yeonaeyong.shopinshop.R
import tk.yeonaeyong.shopinshop.data.model.MapItem
import tk.yeonaeyong.shopinshop.view.map.adapter.MakerInfoRvAdapter
import tk.yeonaeyong.shopinshop.view.map.presenter.MarkerInfoContract
import tk.yeonaeyong.shopinshop.view.map.presenter.MarkerInfoPresenter
import tk.yeonaeyong.shopinshop.view.place.PlaceDetailActivity


class MarkerInfoFragment : Fragment(), MarkerInfoContract.View {
    private val makerInfoRvAdapter = MakerInfoRvAdapter()
    override lateinit var presenter: MarkerInfoContract.Presenter

    override fun placeInfo(placeList: List<MapItem>) {
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
            override fun onClick(mapItem: MapItem) {
                val intent = Intent(requireContext(), PlaceDetailActivity::class.java)
                intent.putExtra(PLACE_NAME, mapItem.name)
                intent.putExtra(PLACE_NUMBER, mapItem.placeNumber)
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