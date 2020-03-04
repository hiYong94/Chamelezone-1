package com.yeonae.chamelezone.view.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.view.mypage.myreview.MyReviewActivity
import kotlinx.android.synthetic.main.fragment_more_button.*


class MoreButtonFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_more_button, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val reviewNumber = arguments?.getInt(REVIEW_NUMBER)
        val placeNumber = arguments?.getInt(PLACE_NUMBER)
        Log.d("More reviewNumber", reviewNumber.toString())

        val data = Intent().apply {
            putExtra(REVIEW_NUMBER, reviewNumber)
        }

        val myReviewData = Intent().apply {
            putExtra(REVIEW_NUMBER, reviewNumber)
            putExtra(PLACE_NUMBER, placeNumber)
        }

        btn_modify.setOnClickListener {
            Toast.makeText(context, "수정", Toast.LENGTH_SHORT).show()

            targetFragment?.onActivityResult(targetRequestCode, BTN_EDIT, Intent())

            dismiss()
        }
        btn_delete.setOnClickListener {
            Toast.makeText(context, "삭제", Toast.LENGTH_SHORT).show()

            targetFragment?.onActivityResult(targetRequestCode, BTN_DELETE, data)

            (context as MyReviewActivity).data(myReviewData)

            dismiss()
        }
    }

    companion object {
        const val REVIEW_NUMBER = "reviewNumber"
        const val PLACE_NUMBER = "placeNumber"
        const val BTN_EDIT = 10
        const val BTN_DELETE = 20

        fun newInstance(placeNumber: Int, reviewNumber: Int) =
            MoreButtonFragment().apply {
                arguments = Bundle().apply {
                    putInt(REVIEW_NUMBER, reviewNumber)
                    putInt(PLACE_NUMBER, placeNumber)
                }
            }
    }
}