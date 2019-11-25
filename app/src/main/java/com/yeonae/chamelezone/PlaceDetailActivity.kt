package com.yeonae.chamelezone

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_place_detail.*
//import me.relex.circleindicator.CircleIndicator


class PlaceDetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_detail)

        val imageAdapter = ImageViewPagerAdapter()
        view.adapter = imageAdapter

//        val indicator = R.id.indicator as CircleIndicator
        tab_layout.setupWithViewPager(view, true);


        review.setOnClickListener {
            val intent = Intent(this, ReviewCreateActivity::class.java)
            startActivity(intent)
        }
    }
}