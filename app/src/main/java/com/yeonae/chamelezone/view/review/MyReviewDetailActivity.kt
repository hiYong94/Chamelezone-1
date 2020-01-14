package com.yeonae.chamelezone.view.review

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.view.review.adapter.ReviewImageVpAdapter
import kotlinx.android.synthetic.main.activity_myreview_detail.*

class MyReviewDetailActivity : AppCompatActivity() {

    val images = intArrayOf(
        R.drawable.img1,
        R.drawable.img2,
        R.drawable.img3,
        R.drawable.img4
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myreview_detail)

        btn_back.setOnClickListener {
            finish()
        }

        val imageAdapter = ReviewImageVpAdapter(images)
        view.adapter = imageAdapter
        tab_layout.setupWithViewPager(view, true)
    }
}