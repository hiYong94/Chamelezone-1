package com.yeonae.chamelezone.view.mypage.myreview

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.network.model.ReviewResponse
import com.yeonae.chamelezone.view.mypage.MoreButtonFragment
import com.yeonae.chamelezone.view.mypage.myreview.adapter.MyReviewRvAdapter
import com.yeonae.chamelezone.view.mypage.myreview.presenter.MyReviewContract
import com.yeonae.chamelezone.view.mypage.myreview.presenter.MyReviewPresenter
import com.yeonae.chamelezone.view.review.MyReviewDetailActivity
import com.yeonae.chamelezone.view.review.ReviewModifyActivity
import kotlinx.android.synthetic.main.activity_my_review.*
import kotlinx.android.synthetic.main.item_my_review.*

class MyReviewActivity : AppCompatActivity(), MyReviewContract.View {
    override lateinit var presenter: MyReviewContract.Presenter
    private val myReviewRvAdapter = MyReviewRvAdapter()

    override fun showMyReviewList(reviewList: List<ReviewResponse>) {
        myReviewRvAdapter.addData(reviewList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_review)
        setAdapter()

        presenter = MyReviewPresenter(
            Injection.reviewRepository(), this
        )

        presenter.userReview(2)

        myReviewRvAdapter.setOnClickListener(object : MyReviewRvAdapter.OnClickListener {
            override fun onClick(review: ReviewResponse) {
                val intent = Intent(this@MyReviewActivity, MyReviewDetailActivity::class.java)
                intent.putExtra("placeName", tv_place_name.text)
                intent.putExtra("content", tv_review_content.text)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            BOTTOM_SHEET -> {
                if (resultCode == MoreButtonFragment.BTN_EDIT || resultCode == MoreButtonFragment.BTN_DELETE) {
                    Toast.makeText(this, "수정 받음", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ReviewModifyActivity::class.java)
                    intent.putExtra("placeName", tv_place_name.text)
                    intent.putExtra("content", tv_review_content.text)
                    startActivity(intent)

                    Toast.makeText(this, "삭제 받음", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "수정 실패", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "삭제 실패", Toast.LENGTH_SHORT).show()
                }
            }
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
    }
}