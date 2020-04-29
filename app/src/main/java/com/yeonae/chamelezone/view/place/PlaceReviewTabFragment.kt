package com.yeonae.chamelezone.view.place

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.ReviewItem
import com.yeonae.chamelezone.ext.shortToast
import com.yeonae.chamelezone.network.room.entity.UserEntity
import com.yeonae.chamelezone.util.Logger
import com.yeonae.chamelezone.view.login.LoginActivity
import com.yeonae.chamelezone.view.mypage.MoreButtonFragment
import com.yeonae.chamelezone.view.mypage.MoreButtonFragment.Companion.BTN_DELETE
import com.yeonae.chamelezone.view.mypage.MoreButtonFragment.Companion.BTN_EDIT
import com.yeonae.chamelezone.view.place.adapter.PlaceReviewTabRvAdapter
import com.yeonae.chamelezone.view.place.presenter.PlaceReviewContract
import com.yeonae.chamelezone.view.place.presenter.PlaceReviewPresenter
import com.yeonae.chamelezone.view.review.ReviewCreateActivity
import com.yeonae.chamelezone.view.review.ReviewImageActivity
import com.yeonae.chamelezone.view.review.ReviewModifyActivity
import kotlinx.android.synthetic.main.fragment_place_review_tab.*

class PlaceReviewTabFragment :
    Fragment(),
    PlaceReviewContract.View {
    override lateinit var presenter: PlaceReviewContract.Presenter
    private lateinit var placeReviewRvAdapter: PlaceReviewTabRvAdapter
    private var reviewNumber = 0
    private var placeNumber = 0
    var memberNumber = 0
    var placeName: String = ""
    private lateinit var reviewItem: ReviewItem

    override fun showPlaceReview(reviewList: List<ReviewItem>) {
        if (::placeReviewRvAdapter.isInitialized)
            placeReviewRvAdapter.addDataList(reviewList)
    }

    override fun showReviewDelete(message: String) {
        requireContext().shortToast(R.string.review_delete)
    }

    override fun showMemberReview(user: UserEntity) {
        memberNumber = user.userNumber ?: 0

        setAdapter()

        placeReviewRvAdapter = PlaceReviewTabRvAdapter(memberNumber)

        placeNumber.let {
            presenter.placeDetailReview(it)
        }

        placeReviewRvAdapter.setItemClickListener(object :
            PlaceReviewTabRvAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int, review: ReviewItem) {
                reviewNumber = review.reviewNumber
                val intent = Intent(context, ReviewImageActivity::class.java)
                intent.putExtra(PLACE_NUMBER, placeNumber)
                intent.putExtra(REVIEW_NUMBER, reviewNumber)
                startActivity(intent)
            }
        })

        placeReviewRvAdapter.setMoreButtonListener(object :
            PlaceReviewTabRvAdapter.MoreButtonListener {
            override fun bottomSheetDialog(review: ReviewItem) {
                reviewItem = review
                reviewNumber = review.reviewNumber
                showBottomSheet(reviewNumber)
            }
        })
    }

    override fun getMemberCheck(response: Boolean) {
        if (response) {
            presenter.getMember()
            btn_review.setOnClickListener {
                placeName = arguments?.getString(PLACE_NAME).orEmpty()
                val intent = Intent(context, ReviewCreateActivity::class.java)
                intent.putExtra(PLACE_NUMBER, placeNumber)
                intent.putExtra(PLACE_NAME, placeName)
                startActivityForResult(intent, ADD_REQUEST)
            }
        } else {
            btn_review.setOnClickListener {
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

        presenter = PlaceReviewPresenter(
            Injection.reviewRepository(), Injection.memberRepository(), this
        )

        presenter.checkMember()

        placeNumber = arguments?.getInt(PLACE_NUMBER) ?: 0
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            BOTTOM_SHEET -> {
                if (resultCode == BTN_EDIT) {
                    reviewNumber = data?.getIntExtra(REVIEW_NUMBER, 0) ?: 0
                    val intent = Intent(context, ReviewModifyActivity::class.java)
                    intent.putExtra(PLACE_NUMBER, placeNumber)
                    intent.putExtra(REVIEW_NUMBER, reviewNumber)
                    intent.putExtra(MEMBER_NUMBER, memberNumber)
                    startActivityForResult(intent, UPDATE_REQUEST)

                } else if (resultCode == BTN_DELETE) {
                    Logger.d("reviewItem $reviewItem")
                    presenter.deleteReview(placeNumber, reviewNumber, memberNumber)
                    placeReviewRvAdapter.removeData(reviewItem)
                }
            }

            ADD_REQUEST -> {
                if (resultCode == RESULT_OK) {
                    presenter.placeDetailReview(placeNumber)
                }
            }

            UPDATE_REQUEST -> {
                if (resultCode == RESULT_OK)
                    presenter.placeDetailReview(placeNumber)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.checkMember()
    }

    private fun setAdapter() {
        recycler_place_review.apply {
            layoutManager = LinearLayoutManager(context)
            if (::placeReviewRvAdapter.isInitialized)
                adapter = placeReviewRvAdapter
        }
    }

    private fun showBottomSheet(reviewNumber: Int) {
        val bottomSheetDialogFragment = MoreButtonFragment.newInstance(placeNumber, reviewNumber)
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
        const val ADD_REQUEST = 101
        const val UPDATE_REQUEST = 102
        private const val PLACE_NUMBER = "placeNumber"
        private const val PLACE_NAME = "placeName"
        private const val MEMBER_NUMBER = "memberNumber"
        private const val REVIEW_NUMBER = "reviewNumber"

        fun newInstance(
            placeNumber: Int,
            placeName: String,
            memberNumber: Int?
        ) =
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