package tk.yeonaeyong.shopinshop.view.map

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_single_info.*
import tk.yeonaeyong.shopinshop.Injection
import tk.yeonaeyong.shopinshop.R
import tk.yeonaeyong.shopinshop.data.model.MapItem
import tk.yeonaeyong.shopinshop.ext.glideImageSet
import tk.yeonaeyong.shopinshop.view.map.presenter.MarkerInfoContract
import tk.yeonaeyong.shopinshop.view.map.presenter.MarkerInfoPresenter
import tk.yeonaeyong.shopinshop.view.place.PlaceDetailActivity

class SingleInfoFragment : Fragment(), MarkerInfoContract.View {
    override lateinit var presenter: MarkerInfoContract.Presenter
    var placeNumber: Int = 0
    var placeName: String = ""

    override fun placeInfo(placeList: List<MapItem>) {
        placeName = placeList[0].name
        tv_place_name.text = placeName
        placeNumber = placeList[0].placeNumber
        tv_place_keyword.text = placeList[0].keyword
        tv_place_address.text = placeList[0].address
        iv_place_image.glideImageSet(
            placeList[0].image, iv_place_image.measuredWidth,
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
        presenter = MarkerInfoPresenter(
            Injection.placeRepository(), this
        )
        arguments?.getString("searchWord")?.let { presenter.searchPlace(it) }

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