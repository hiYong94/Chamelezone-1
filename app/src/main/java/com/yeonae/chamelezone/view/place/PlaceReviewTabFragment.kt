package com.yeonae.chamelezone.view.place

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonae.chamelezone.App
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.ReviewItem
import com.yeonae.chamelezone.network.room.entity.UserEntity
import com.yeonae.chamelezone.view.login.LoginActivity
import com.yeonae.chamelezone.view.mypage.MoreButtonFragment
import com.yeonae.chamelezone.view.place.adapter.PlaceReviewTabRvAdapter
import com.yeonae.chamelezone.view.place.presenter.PlaceReviewContract
import com.yeonae.chamelezone.view.place.presenter.PlaceReviewPresenter
import com.yeonae.chamelezone.view.review.ReviewCreateActivity
import com.yeonae.chamelezone.view.review.ReviewImageActivity
import com.yeonae.chamelezone.view.review.ReviewModifyActivity
import kotlinx.android.synthetic.main.fragment_place_review_tab.*
import kotlinx.android.synthetic.main.item_place_review.*

class PlaceReviewTabFragment : Fragment(), PlaceReviewContract.View {
    override lateinit var presenter: PlaceReviewContract.Presenter
    private lateinit var placeReviewRvAdapter: PlaceReviewTabRvAdapter
    private var reviewNumber = 0
    private var placeNumber = 0
    private var memberNumber = 0
    var reviewMemberNumber = 0
    var placeName: String = ""


    override fun showPlaceReview(reviewList: List<ReviewItem>) {
        if (::placeReviewRvAdapter.isInitialized)
            placeReviewRvAdapter.addData(reviewList)
    }

    override fun showReviewDelete(message: String) {
        Toast.makeText(context, "리뷰가 삭제되었습니다", Toast.LENGTH_LONG).show()
    }

    override fun showMemberReview(user: UserEntity) {
        reviewMemberNumber = placeReviewRvAdapter.reviewMemberNumber
        Log.d("PlaceReviewTabFragment memberNumber", memberNumber.toString())
        Log.d("PlaceReviewTabFragment reviewMemberNumber2", reviewMemberNumber.toString())
        if (memberNumber == reviewMemberNumber) {
            btn_more.isVisible = true
        } else if (memberNumber == 0) {
            btn_more.isInvisible = true
        }
    }

    override fun getMemberCheck(response: Boolean) {
        presenter.getMember()
        if (response) {
            review.setOnClickListener {
                placeName = arguments?.getString(PLACE_NAME).toString()
                val intent = Intent(context, ReviewCreateActivity::class.java)
//            intent.putExtra(PLACE_NAME, "$tv_place_name")
                Log.d("placeNumber", placeNumber.toString())
                Log.d("PlaceReviewTabFragment placeName", placeName.toString())
                intent.putExtra(PLACE_NUMBER, placeNumber)
                intent.putExtra(PLACE_NAME, placeName)
                startActivity(intent)
            }
        } else {
            review.setOnClickListener {
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_place_review_tab, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        placeNumber = arguments?.getInt(PLACE_NUMBER) ?: 0
        memberNumber = arguments?.getInt(MEMBER_NUMBER) ?: 0
        Log.d("memberNumber", memberNumber.toString())

        placeReviewRvAdapter = PlaceReviewTabRvAdapter()

        setAdapter()

        presenter = PlaceReviewPresenter(
            Injection.reviewRepository(), Injection.memberRepository(App.instance.context()), this
        )

        presenter.checkMember()

        placeNumber.let {
            presenter.placeDetailReview(it)
        }

        placeReviewRvAdapter.setItemClickListener(object :
            PlaceReviewTabRvAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int, review: ReviewItem) {
                reviewNumber = review.reviewNumber
                reviewMemberNumber = review.memberNumber
                Log.d("PlaceReviewTabFragment reviewMemberNumber1", reviewMemberNumber.toString())
                val intent = Intent(context, ReviewImageActivity::class.java)
                intent.putExtra(PLACE_NUMBER, placeNumber)
                intent.putExtra(REVIEW_NUMBER, reviewNumber)
                Log.d("rr PlaceReviewTabFragment placeNumber2", placeNumber.toString())
                Log.d("rr PlaceReviewTabFragment reviewNumber2", reviewNumber.toString())
                startActivity(intent)
            }
        })

        placeReviewRvAdapter.setMoreButtonListener(object :
            PlaceReviewTabRvAdapter.MoreButtonListener {
            override fun bottomSheetDialog(review: ReviewItem) {
                reviewNumber = review.reviewNumber
                showBottomSheet(reviewNumber)
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            BOTTOM_SHEET -> {
                if (resultCode == MoreButtonFragment.BTN_EDIT) {
                    reviewNumber = data?.getIntExtra(REVIEW_NUMBER, 0) ?: 0
                    Toast.makeText(context, "수정 받음", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, ReviewModifyActivity::class.java)
                    startActivity(intent)

                } else if (resultCode == MoreButtonFragment.BTN_DELETE) {
                    Toast.makeText(context, "삭제 받음", Toast.LENGTH_SHORT).show()
                    presenter.deleteReview(placeNumber, reviewNumber, memberNumber)
                    Log.d("placeDetailReviewTab reviewNumber", reviewNumber.toString())
                    Log.d("placeDetailReviewTab placeNumber", placeNumber.toString())
                    Log.d("placeDetailReviewTab memberNumber", memberNumber.toString())
                }
            }
        }
    }

    private fun setAdapter() {
        recycler_place_review.apply {
            layoutManager = LinearLayoutManager(context)
            if (::placeReviewRvAdapter.isInitialized)
                adapter = placeReviewRvAdapter
        }
    }

    private fun showBottomSheet(reviewNumber: Int) {
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
        private const val MEMBER_NUMBER = "memberNumber"
        private const val REVIEW_NUMBER = "reviewNumber"

        fun newInstance(placeNumber: Int, placeName: String, memberNumber: Int?) =
            PlaceReviewTabFragment().apply {
                arguments = Bundle().apply {
                    putInt(PLACE_NUMBER, placeNumber)
                    putString(PLACE_NAME, placeName)
                    if (memberNumber != null) {
                        putInt(MEMBER_NUMBER, memberNumber)
                    }
                }
            }
    }

}