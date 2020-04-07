package com.yeonae.chamelezone.view.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.util.Logger
import com.yeonae.chamelezone.view.place.PlaceReviewTabFragment
import kotlinx.android.synthetic.main.fragment_more_button.*


class MoreButtonFragment : BottomSheetDialogFragment() {
    private val reviewNumber = arguments?.getInt(REVIEW_NUMBER)
    private lateinit var onModifyClickListener: OnModifyClickListener
    private lateinit var onDeleteClickListener: OnDeleteClickListener

    interface OnModifyClickListener {
        fun onModifyClick()
    }

    interface OnDeleteClickListener {
        fun onDeleteClick()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_more_button, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = Intent().apply {
            putExtra(REVIEW_NUMBER, reviewNumber)
        }

        btn_modify.setOnClickListener {
            if (targetRequestCode == PlaceReviewTabFragment.BOTTOM_SHEET) {
                targetFragment?.onActivityResult(targetRequestCode, BTN_EDIT, Intent())
            } else {
                onModifyClickListener.onModifyClick()
            }
            dismiss()
        }
        btn_delete.setOnClickListener {
            if (targetRequestCode ==  PlaceReviewTabFragment.BOTTOM_SHEET) {
                targetFragment?.onActivityResult(targetRequestCode, BTN_DELETE, data)
            } else {
                onDeleteClickListener.onDeleteClick()
            }
            dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onModifyClickListener = (context as OnModifyClickListener)
        onDeleteClickListener = (context as OnDeleteClickListener)
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