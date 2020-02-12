package com.yeonae.chamelezone.view.place

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.adapter.ImageViewPagerAdapter
import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.view.place.adapter.PlaceDetailPagerAdapter
import com.yeonae.chamelezone.view.place.presenter.PlaceInfoContract
import com.yeonae.chamelezone.view.place.presenter.PlaceInfoPresenter
import kotlinx.android.synthetic.main.activity_place_detail.*

class PlaceDetailActivity : AppCompatActivity(), PlaceInfoContract.View {
    override fun placeInfo(place: PlaceResponse) {
        val placeImages = place.savedImageName.split(",")
        val images = arrayListOf<String>()
        Log.d("placeImages", placeImages.toString())
        for(i in placeImages.indices){
            images.add("http://13.209.136.122:3000/image/"+ placeImages[i])
        }
        val imageAdapter = ImageViewPagerAdapter(images)
        view.adapter = imageAdapter
    }

    override lateinit var presenter: PlaceInfoContract.Presenter
    private val PLACE_NAME = "placeName"
    private val PLACE_NUMBER = "placeNumber"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_detail)
        val placeName = intent.getStringExtra(PLACE_NAME)
        val placeNumber = intent.getIntExtra(PLACE_NUMBER, 0)
        tv_place_name.text = placeName

        presenter = PlaceInfoPresenter(
            Injection.placeRepository(), this
        )

        presenter.placeDetail(placeNumber)

        btn_back.setOnClickListener {
            finish()
        }

        tab_layout.setupWithViewPager(view, true)

        val fragmentAdapter = PlaceDetailPagerAdapter(supportFragmentManager, placeNumber)
        viewpager_detail.adapter = fragmentAdapter
        tabs_detail.setupWithViewPager(viewpager_detail)
    }
}