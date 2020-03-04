package com.yeonae.chamelezone.view.mypage.myreview

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.Url.IMAGE_RESOURCE
import com.yeonae.chamelezone.network.model.ReviewResponse
import com.yeonae.chamelezone.view.mypage.myreview.adapter.ReviewImageVpAdapter
import com.yeonae.chamelezone.view.mypage.myreview.presenter.MyReviewDetailContract
import com.yeonae.chamelezone.view.mypage.myreview.presenter.MyReviewDetailPresenter
import kotlinx.android.synthetic.main.activity_myreview_detail.*

class MyReviewDetailActivity : AppCompatActivity(), MyReviewDetailContract.View {
    override lateinit var presenter: MyReviewDetailContract.Presenter
    private var placeNumber = 0
    private var reviewNumber = 0

    override fun showMyReviewDetail(review: ReviewResponse) {
        val reviewImages = review.savedImageName.split(",")
        val imageList = reviewImages.map {
            IMAGE_RESOURCE + it
        }
        Log.d("imageList imageList", imageList.toString())
        Log.d("imageList imageList", reviewImages.toString())

        val imageAdapter = ReviewImageVpAdapter(imageList)
        vp_image.adapter = imageAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myreview_detail)
        placeNumber = intent.getIntExtra(PLACE_NUMBER, 0)
        reviewNumber = intent.getIntExtra(REVIEW_NUMBER, 0)

        presenter = MyReviewDetailPresenter(
            Injection.reviewRepository(), this
        )

        presenter.getMyReviewDetail(placeNumber, reviewNumber)

        tv_place_title.text = intent.getStringExtra(PLACE_NAME)
        tv_content.text = intent.getStringExtra(REVIEW_CONTENT)

        tab_layout.setupWithViewPager(vp_image, true)

        btn_back.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val PLACE_NAME = "placeName"
        const val REVIEW_CONTENT = "content"
        const val PLACE_NUMBER = "placeNumber"
        const val REVIEW_NUMBER = "reviewNumber"
    }
}