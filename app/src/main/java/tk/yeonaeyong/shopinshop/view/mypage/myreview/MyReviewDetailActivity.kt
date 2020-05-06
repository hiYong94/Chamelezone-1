package tk.yeonaeyong.shopinshop.view.mypage.myreview

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_myreview_detail.*
import tk.yeonaeyong.shopinshop.Injection
import tk.yeonaeyong.shopinshop.R
import tk.yeonaeyong.shopinshop.data.model.ReviewItem
import tk.yeonaeyong.shopinshop.ext.Url.IMAGE_RESOURCE
import tk.yeonaeyong.shopinshop.view.mypage.myreview.adapter.ReviewImageVpAdapter
import tk.yeonaeyong.shopinshop.view.mypage.myreview.presenter.MyReviewDetailContract
import tk.yeonaeyong.shopinshop.view.mypage.myreview.presenter.MyReviewDetailPresenter

class MyReviewDetailActivity : AppCompatActivity(), MyReviewDetailContract.View {
    override lateinit var presenter: MyReviewDetailContract.Presenter
    private lateinit var imagePagerAdapter: ReviewImageVpAdapter
    private var placeNumber = 0
    private var reviewNumber = 0

    override fun showMyReviewDetail(review: ReviewItem) {
        val reviewImages = review.images
        val imageList = arrayListOf<String>()
        reviewImages.forEachIndexed { index, _ ->
            imageList.add(IMAGE_RESOURCE + reviewImages[index])
        }
        Log.d("imageList imageList reviewImages", reviewImages.toString())
        Log.d("imageList imageList imageList", imageList.toString())

        imagePagerAdapter = ReviewImageVpAdapter(imageList, placeNumber, reviewNumber)

        if (::imagePagerAdapter.isInitialized)
            vp_image.adapter = imagePagerAdapter
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