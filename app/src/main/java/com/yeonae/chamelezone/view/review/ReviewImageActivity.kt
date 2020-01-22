package com.yeonae.chamelezone.view.review

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.view.place.adapter.PlaceReviewImageVpAdapter
import kotlinx.android.synthetic.main.activity_review_image.*

class ReviewImageActivity : AppCompatActivity() {

    private val images = intArrayOf(
        R.drawable.img1,
        R.drawable.img2,
        R.drawable.img3,
        R.drawable.img4
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_image)

        btn_back.setOnClickListener {
            finish()
        }

        val imageAdapter = PlaceReviewImageVpAdapter(images)
        view_image.adapter = imageAdapter
        tab_layout.setupWithViewPager(view_image, true)
    }
}