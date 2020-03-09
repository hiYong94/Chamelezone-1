package com.yeonae.chamelezone.view.mypage.myreview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonae.chamelezone.App
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.ReviewItem
import com.yeonae.chamelezone.network.room.entity.UserEntity
import com.yeonae.chamelezone.view.mypage.MoreButtonFragment
import com.yeonae.chamelezone.view.mypage.myreview.adapter.MyReviewRvAdapter
import com.yeonae.chamelezone.view.mypage.myreview.presenter.MyReviewContract
import com.yeonae.chamelezone.view.mypage.myreview.presenter.MyReviewPresenter
import kotlinx.android.synthetic.main.activity_my_review.*

class MyReviewActivity : AppCompatActivity(), MyReviewContract.View {
    override lateinit var presenter: MyReviewContract.Presenter
    private val myReviewRvAdapter = MyReviewRvAdapter()
    private var placeNumber = 0
    private var reviewNumber = 0
    private var memberNumber: Int = 0

    override fun getMember(user: UserEntity) {
        memberNumber = user.userNumber ?: 0
        memberNumber.let { presenter.getUserReview(it) }
        Log.d("myReviewList memberNumber", user.userNumber.toString())
    }

    override fun getMemberCheck(response: Boolean) {
        presenter.getMember()
    }

    override fun showMyReviewList(reviewList: List<ReviewItem>) {
        myReviewRvAdapter.addData(reviewList)
    }

    override fun showReviewDelete(message: String) {
        Toast.makeText(this, "리뷰가 삭제되었습니다", Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_review)
        setAdapter()

        presenter = MyReviewPresenter(
            Injection.reviewRepository(), Injection.memberRepository(App.instance.context()), this
        )

        presenter.checkMember()

        myReviewRvAdapter.setOnClickListener(object : MyReviewRvAdapter.OnClickListener {
            override fun onClick(review: ReviewItem) {
                val placeName = review.name
                val reviewContent = review.content
                placeNumber = review.placeNumber
                reviewNumber = review.reviewNumber
                val intent = Intent(this@MyReviewActivity, MyReviewDetailActivity::class.java)
                intent.putExtra(PLACE_NUMBER, placeNumber)
                intent.putExtra(REVIEW_NUMBER, reviewNumber)
                intent.putExtra(PLACE_NAME, placeName)
                intent.putExtra(REVIEW_CONTENT, reviewContent)
                this@MyReviewActivity.startActivity(intent)
            }
        })

        myReviewRvAdapter.setMoreButtonListener(object : MyReviewRvAdapter.MoreButtonListener {
            override fun bottomSheetDialog(review: ReviewItem) {
                placeNumber = review.placeNumber
                reviewNumber = review.reviewNumber
                showBottomSheet(placeNumber, reviewNumber)
            }
        })

        btn_back.setOnClickListener {
            finish()
        }
    }

    private fun showBottomSheet(placeNumber: Int, reviewNumber: Int) {
        val bottomSheetDialogFragment = MoreButtonFragment()
        bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
    }

    private fun setAdapter() {
        recycler_my_review.layoutManager = LinearLayoutManager(this)
        recycler_my_review.adapter = myReviewRvAdapter
    }

    fun data(intent: Intent) {
        intent.getIntExtra(REVIEW_NUMBER, 0)
        intent.getIntExtra(PLACE_NUMBER, 0)
        Toast.makeText(this, "삭제 받음", Toast.LENGTH_SHORT).show()
        Log.d("MyReviewActivity reviewNumber", reviewNumber.toString())
        Log.d("MyReviewActivity placeNumber", placeNumber.toString())
        Log.d("MyReviewActivity memberNumber", memberNumber.toString())
        presenter.deleteReview(placeNumber, reviewNumber, memberNumber)
    }

    companion object {
        const val BOTTOM_SHEET = 100
        const val PLACE_NAME = "placeName"
        const val REVIEW_CONTENT = "content"
        const val PLACE_NUMBER = "placeNumber"
        const val REVIEW_NUMBER = "reviewNumber"
    }
}