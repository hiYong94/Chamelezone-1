package com.yeonae.chamelezone.view.place

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.Url
import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.view.place.adapter.PlaceImageDetailVpAdapter
import com.yeonae.chamelezone.view.place.presenter.PlaceImageDetailContract
import com.yeonae.chamelezone.view.place.presenter.PlaceImageDetailPresenter
import kotlinx.android.synthetic.main.activity_place_image.*

class PlaceImageDetailActivity :
    AppCompatActivity(),
    PlaceImageDetailContract.View {
    override lateinit var presenter: PlaceImageDetailContract.Presenter
    private lateinit var imageAdapter: PlaceImageDetailVpAdapter
    private var position = 0

    override fun showPlaceImage(place: PlaceResponse) {
        val images = arrayListOf<String>()
        place.savedImageName.forEach {
            images.add(Url.IMAGE_RESOURCE + it)
        }
        imageAdapter =
            PlaceImageDetailVpAdapter(
                images
            )
        vp_image.adapter = imageAdapter
        vp_image.currentItem = position
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_image)

        val placeNumber = intent.getIntExtra(PLACE_NUMBER, 0)
        val memberNumber = intent.getIntExtra(MEMBER_NUMBER, 0)

        tab_layout.setupWithViewPager(vp_image, true)
        position = intent.getIntExtra(POSITION, 0)

        presenter = PlaceImageDetailPresenter(
            Injection.placeRepository(), this
        )

        presenter.getPlace(placeNumber, memberNumber)
    }

    companion object {
        const val PLACE_NUMBER = "placeNumber"
        const val MEMBER_NUMBER = "memberNumber"
        const val POSITION = "position"
    }
}