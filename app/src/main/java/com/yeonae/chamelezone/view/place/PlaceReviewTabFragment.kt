package com.yeonae.chamelezone.view.place

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.view.review.ReviewCreateActivity
import kotlinx.android.synthetic.main.fragment_place_review_tab.*

class PlaceReviewTabFragment : Fragment() {

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
    }
}