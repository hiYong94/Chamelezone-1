package com.yeonae.chamelezone.view.mypage.myreview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.Review
import com.yeonae.chamelezone.view.mypage.myreview.adapter.MyReviewRvAdapter
import kotlinx.android.synthetic.main.activity_my_review.*

class MyReviewActivity : AppCompatActivity() {
    private val myReviewList = arrayListOf(
        Review("구슬모아당구장", "여기 진짜 존예ㅠㅠ 다음에 또 방문할 예정이에요."),
        Review("커피빈 강남오토스퀘어점", "ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ" +
                "ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ")
    )
    private val myReviewRvAdapter = MyReviewRvAdapter(myReviewList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_review)
        setAdapter()

        myReviewRvAdapter.setOnClickListener(object : MyReviewRvAdapter.OnClickListener {
            override fun onClick(review: Review) {

            }
        })

        btn_back.setOnClickListener {
            finish()
        }
    }
    private fun setAdapter() {
        recycler_my_review.layoutManager = LinearLayoutManager(this)
        recycler_my_review.adapter = myReviewRvAdapter
    }
}