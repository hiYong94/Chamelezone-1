package com.yeonae.chamelezone.view.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.model.Like
import com.yeonae.chamelezone.view.place.adapter.PlaceReviewTabRvAdapter
import kotlinx.android.synthetic.main.fragment_place_review_tab.*

class PlaceReviewTabFragment : Fragment() {
    private val placeReviewRvAdapter = PlaceReviewTabRvAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_place_review_tab, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setAdapter()

        placeReviewRvAdapter.setOnClickListener(object : PlaceReviewTabRvAdapter.OnClickListener {
            override fun onClick(like: Like) {

            }
        })

    }

    private fun setAdapter() {
        recycler_place_review.layoutManager = LinearLayoutManager(context)
        recycler_place_review.adapter = placeReviewRvAdapter
    }
}