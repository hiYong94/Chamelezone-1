package com.yeonae.chamelezone.view.review

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.network.model.ReviewResponse
import com.yeonae.chamelezone.view.review.adapter.PlaceReviewImageVpAdapter
import com.yeonae.chamelezone.view.review.presenter.ReviewDetailContract
import com.yeonae.chamelezone.view.review.presenter.ReviewDetailPresenter
import kotlinx.android.synthetic.main.activity_review_image.*

class ReviewImageActivity : AppCompatActivity(), ReviewDetailContract.View {
    override lateinit var presenter: ReviewDetailContract.Presenter

    override fun showReviewImage(review: ReviewResponse) {
        val reviewImages = review.savedImageName.split(",")
        val imageList = reviewImages.map {
            "http://13.209.136.122:3000/image/$it"
        }
        val imageAdapter = PlaceReviewImageVpAdapter(imageList)
        view_image.adapter = imageAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_image)

        val placeNumber = intent.getIntExtra(PLACE_NUMBER, 0)
        val reviewNumber = intent.getIntExtra(REVIEW_NUMBER, 0)

        presenter = ReviewDetailPresenter(
            Injection.reviewRepository(), this
        )

        presenter.getReview(placeNumber, reviewNumber)

        btn_back.setOnClickListener {
            finish()
        }
        tab_layout.setupWithViewPager(view_image, true)
    }

    companion object {
        private const val PLACE_NUMBER = "placeNumber"
        private const val REVIEW_NUMBER = "reviewNumber"
    }
}