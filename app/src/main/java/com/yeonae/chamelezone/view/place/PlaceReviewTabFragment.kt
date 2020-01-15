package com.yeonae.chamelezone.view.place

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.Review
import com.yeonae.chamelezone.view.place.adapter.PlaceReviewTabRvAdapter
import com.yeonae.chamelezone.view.review.ReviewCreateActivity
import com.yeonae.chamelezone.view.review.ReviewImageActivity
import kotlinx.android.synthetic.main.fragment_place_review_tab.*

class PlaceReviewTabFragment : Fragment() {

    private val placeReviewList = arrayListOf(
        Review("yeonjae22", "어제", "place1", "여기 진짜 분위기 이뻐요"),
        Review("Lsunae", "이틀전", "place2", "다시 가고 싶은 곳이에요!"),
        Review("hiyong", "일주일전",  "place3", "혼자 가도 좋은거같아요")
    )

    private val images = intArrayOf(
        R.drawable.img1,
        R.drawable.img2,
        R.drawable.img3,
        R.drawable.img4
    )

    private val placeReviewRvAdapter = PlaceReviewTabRvAdapter(placeReviewList)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_place_review_tab, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        review.setOnClickListener {
            val intent = Intent(context, ReviewCreateActivity::class.java)
            startActivity(intent)
        }

        setAdapter()

        recycler_place_review.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                val intent = Intent(context, ReviewImageActivity::class.java)
                startActivity(intent)

                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

            }
        })
    }

    private fun setAdapter() {
        recycler_place_review.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = placeReviewRvAdapter
        }
    }
}