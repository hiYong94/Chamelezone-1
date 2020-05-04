package com.yeonae.chamelezone.view.mypage.myreview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.ReviewItem
import com.yeonae.chamelezone.ext.Url
import com.yeonae.chamelezone.view.mypage.myreview.adapter.MyReviewImageDetailVpAdapter
import com.yeonae.chamelezone.view.mypage.myreview.presenter.MyReviewImageDetailContract
import com.yeonae.chamelezone.view.mypage.myreview.presenter.MyReviewImageDetailPresenter
import kotlinx.android.synthetic.main.activity_review_image.*

class MyReviewImageDetailActivity :
    AppCompatActivity(),
    MyReviewImageDetailContract.View {
    override lateinit var presenter: MyReviewImageDetailContract.Presenter
    private var position = 0

    override fun showReviewImage(review: ReviewItem) {
        val reviewImages = review.images
        val imageList = reviewImages.map {
            Url.IMAGE_RESOURCE + it
        }
        val imageAdapter = MyReviewImageDetailVpAdapter(imageList)
        view_image.adapter = imageAdapter
        view_image.currentItem = position
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myreview_image_detail)

        val placeNumber = intent.getIntExtra(PLACE_NUMBER, 0)
        val reviewNumber = intent.getIntExtra(REVIEW_NUMBER, 0)
        position = intent.getIntExtra(POSITION, 0)

        presenter = MyReviewImageDetailPresenter(
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
        private const val POSITION = "position"
    }
}