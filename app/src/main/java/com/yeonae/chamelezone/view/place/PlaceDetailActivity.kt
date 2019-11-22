package com.yeonae.chamelezone.view.place

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.adapter.ImageViewPagerAdapter
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.view.review.ReviewCreateActivity
import kotlinx.android.synthetic.main.activity_place_detail.*

class PlaceDetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_detail)

        val imageAdapter = ImageViewPagerAdapter()
        view.adapter = imageAdapter

        review.setOnClickListener {
            val intent = Intent(this, ReviewCreateActivity::class.java)
            startActivity(intent)
        }
    }
}