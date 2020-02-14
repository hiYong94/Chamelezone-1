package com.yeonae.chamelezone.view.place

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.adapter.ImageViewPagerAdapter
import com.yeonae.chamelezone.view.place.adapter.PlaceDetailPagerAdapter
import kotlinx.android.synthetic.main.activity_place_detail.*
import kotlin.math.abs


class PlaceDetailActivity : AppCompatActivity() {


    companion object{
        private const val PLACE_NAME = "placeName"
        private const val PLACE_NUMBER = "placeNumber"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_detail)

        vp_image.post {
            vp_image.layoutParams = vp_image.layoutParams.apply {
                height = ((vp_image.parent as ViewGroup).width / 3) * 2
            }

            setupView()
        }

    }

    private fun setupView(){
        val placeName = intent.getStringExtra(PLACE_NAME)
        val placeNumber = intent.getIntExtra(PLACE_NUMBER, 0)
        Log.d("placeNumber detail", placeNumber.toString())
        tv_place_name.text = placeName

        btn_back.setOnClickListener {
            finish()
        }

        val imageAdapter = ImageViewPagerAdapter()
        vp_image.adapter = imageAdapter
        tab_layout.setupWithViewPager(vp_image, true)

        val fragmentAdapter = PlaceDetailPagerAdapter(supportFragmentManager, placeNumber)
        viewpager_detail.adapter = fragmentAdapter
        tabs_detail.setupWithViewPager(viewpager_detail)

        tool_bar.run {
            post {
                val nameBar = layout_visibility.height
                val tabBar = tabs_detail.height

                Log.d("PlaceDetailActivity nameBar", nameBar.toString())
                Log.d("PlaceDetailActivity tabBar", tabBar.toString())
                layoutParams = tool_bar.layoutParams.apply {
                    height = nameBar + tabBar
                }

                app_bar.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
                    if (abs(verticalOffset)-appBarLayout.totalScrollRange == 0) {
                        layout_visibility.visibility = View.VISIBLE
                    } else {
                        layout_visibility.visibility = View.INVISIBLE
                    }
                })
            }
        }
    }
}