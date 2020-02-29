package com.yeonae.chamelezone.view.mypage.myreview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.network.model.ReviewResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity
import com.yeonae.chamelezone.view.mypage.MoreButtonFragment
import com.yeonae.chamelezone.view.mypage.myreview.adapter.MyReviewRvAdapter
import com.yeonae.chamelezone.view.mypage.myreview.presenter.MyReviewContract
import com.yeonae.chamelezone.view.mypage.myreview.presenter.MyReviewPresenter
import kotlinx.android.synthetic.main.activity_my_review.*
import kotlinx.android.synthetic.main.item_my_review.*

class MyReviewActivity : AppCompatActivity(), MyReviewContract.View {
    override lateinit var presenter: MyReviewContract.Presenter
    private val myReviewRvAdapter = MyReviewRvAdapter()
    private var placeNumber = 0
    private var reviewNumber = 0

    override fun getMember(user: UserEntity) {
        user.userNumber?.let { presenter.getUserReview(it) }
        Log.d("myReviewList memberNumber", user.userNumber.toString())
    }

    override fun getMemberCheck(response: Boolean) {
        presenter.getMember()
    }

    override fun showMyReviewList(reviewList: List<ReviewResponse>) {
        myReviewRvAdapter.addData(reviewList)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_review)
        setAdapter()

        presenter = MyReviewPresenter(
            Injection.reviewRepository(), Injection.memberRepository(applicationContext), this
        )

        presenter.checkMember()

        myReviewRvAdapter.setOnClickListener(object : MyReviewRvAdapter.OnClickListener {
            override fun onClick(review: ReviewResponse) {
                placeNumber = review.placeNumber
                reviewNumber = review.reviewNumber
                val intent = Intent(this@MyReviewActivity, MyReviewDetailActivity::class.java)
                intent.putExtra(PLACE_NUMBER, placeNumber)
                intent.putExtra(REVIEW_NUMBER, reviewNumber)
                intent.putExtra(PLACE_NAME, tv_place_name.text)
                intent.putExtra(REVIEW_CONTENT, tv_review_content.text)
                this@MyReviewActivity.startActivity(intent)
            }
        })

        myReviewRvAdapter.setMoreButtonListener(object : MyReviewRvAdapter.MoreButtonListener {
            override fun bottomSheetDialog() {
                showBottomSheet()
            }
        })

        btn_back.setOnClickListener {
            finish()
        }

    }

    private fun showBottomSheet() {
        val bottomSheetDialogFragment = MoreButtonFragment()
        bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
    }

    private fun setAdapter() {
        recycler_my_review.layoutManager = LinearLayoutManager(this)
        recycler_my_review.adapter = myReviewRvAdapter
    }

    companion object {
        const val BOTTOM_SHEET = 100
        const val PLACE_NAME = "placeName"
        const val REVIEW_CONTENT = "content"
        const val PLACE_NUMBER = "placeNumber"
        const val REVIEW_NUMBER = "reviewNumber"
    }
}