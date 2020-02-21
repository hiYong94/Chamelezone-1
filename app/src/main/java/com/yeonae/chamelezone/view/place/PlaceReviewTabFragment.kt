package com.yeonae.chamelezone.view.place

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.network.model.ReviewResponse
import com.yeonae.chamelezone.view.mypage.MoreButtonFragment
import com.yeonae.chamelezone.view.place.adapter.PlaceReviewTabRvAdapter
import com.yeonae.chamelezone.view.place.presenter.PlaceReviewContract
import com.yeonae.chamelezone.view.place.presenter.PlaceReviewPresenter
import com.yeonae.chamelezone.view.review.ReviewCreateActivity
import com.yeonae.chamelezone.view.review.ReviewImageActivity
import com.yeonae.chamelezone.view.review.ReviewModifyActivity
import kotlinx.android.synthetic.main.activity_place_detail.*
import kotlinx.android.synthetic.main.fragment_place_review_tab.*

class PlaceReviewTabFragment : Fragment(), PlaceReviewContract.View {
    override lateinit var presenter: PlaceReviewContract.Presenter

    override fun showPlaceReview(reviewList: List<ReviewResponse>) {
        placeReviewRvAdapter.addData(reviewList)
    }

    private val placeReviewRvAdapter = PlaceReviewTabRvAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_place_review_tab, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val placeNumber = arguments?.getInt(PLACE_NUMBER)
        val placeName = arguments?.getString(PLACE_NAME)

        setAdapter()

        presenter = PlaceReviewPresenter(
            Injection.reviewRepository(), this
        )

        placeNumber?.let { presenter.placeDetailReview(it) }

        placeReviewRvAdapter.setItemClickListener(object :
            PlaceReviewTabRvAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(context, ReviewImageActivity::class.java)
                startActivity(intent)
            }
        })

        placeReviewRvAdapter.setMoreButtonListener(object :
            PlaceReviewTabRvAdapter.MoreButtonListener {
            override fun bottomSheetDialog() {
                showBottomSheet()
            }
        })

        Log.d("placeNumber", placeNumber.toString())
        review.setOnClickListener {
            val intent = Intent(context, ReviewCreateActivity::class.java)
            intent.putExtra("placeName", "$tv_place_name")
            intent.putExtra(PLACE_NUMBER, placeNumber)
            intent.putExtra(PLACE_NAME, placeName)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            BOTTOM_SHEET -> {
                if (resultCode == MoreButtonFragment.BTN_EDIT || resultCode == MoreButtonFragment.BTN_DELETE) {
                    Toast.makeText(context, "수정 받음", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, ReviewModifyActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(context, "삭제 받음", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "수정 실패", Toast.LENGTH_SHORT).show()
                    Toast.makeText(context, "삭제 실패", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setAdapter() {
        recycler_place_review.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = placeReviewRvAdapter
        }
    }

    private fun showBottomSheet() {
        val bottomSheetDialogFragment = MoreButtonFragment()
        bottomSheetDialogFragment.setTargetFragment(this, BOTTOM_SHEET)
        activity?.supportFragmentManager?.let {
            bottomSheetDialogFragment.show(
                it,
                bottomSheetDialogFragment.tag
            )
        }

    }

    companion object {
        const val BOTTOM_SHEET = 100
        private const val PLACE_NUMBER = "placeNumber"
        private const val PLACE_NAME = "placeName"

        fun newInstance(placeNumber: Int, placeName: String) = PlaceReviewTabFragment().apply {
            arguments = Bundle().apply {
                putInt(PLACE_NUMBER, placeNumber)
                putString(PLACE_NAME, placeName)
            }
        }
    }
}