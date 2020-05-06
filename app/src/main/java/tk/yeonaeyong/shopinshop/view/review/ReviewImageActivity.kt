package tk.yeonaeyong.shopinshop.view.review

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_review_image.*
import tk.yeonaeyong.shopinshop.Injection
import tk.yeonaeyong.shopinshop.R
import tk.yeonaeyong.shopinshop.ext.Url.IMAGE_RESOURCE
import tk.yeonaeyong.shopinshop.network.model.ReviewResponse
import tk.yeonaeyong.shopinshop.view.review.adapter.PlaceReviewImageVpAdapter
import tk.yeonaeyong.shopinshop.view.review.presenter.ReviewDetailContract
import tk.yeonaeyong.shopinshop.view.review.presenter.ReviewDetailPresenter

class ReviewImageActivity : AppCompatActivity(), ReviewDetailContract.View {
    override lateinit var presenter: ReviewDetailContract.Presenter

    override fun showReviewImage(review: ReviewResponse) {
        val reviewImages = review.savedImageName
        val imageList = reviewImages.map {
            IMAGE_RESOURCE + it
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