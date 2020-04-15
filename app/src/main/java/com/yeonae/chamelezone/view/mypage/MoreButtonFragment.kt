package com.yeonae.chamelezone.view.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.view.place.PlaceReviewTabFragment
import kotlinx.android.synthetic.main.fragment_more_button.*

class MoreButtonFragment : BottomSheetDialogFragment() {
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

        val reviewNumber = arguments?.getInt(REVIEW_NUMBER)

        val data = Intent().apply {
            putExtra(REVIEW_NUMBER, reviewNumber)
        }

        btn_modify.setOnClickListener {
            if (targetRequestCode == PlaceReviewTabFragment.BOTTOM_SHEET) {
                targetFragment?.onActivityResult(targetRequestCode, BTN_EDIT, data)
            } else {
                if (::onModifyClickListener.isInitialized)
                    onModifyClickListener.onModifyClick()
            }
            dismiss()
        }
        btn_delete.setOnClickListener {
            if (targetRequestCode == PlaceReviewTabFragment.BOTTOM_SHEET) {
                targetFragment?.onActivityResult(targetRequestCode, BTN_DELETE, data)
            } else {
                if (::onDeleteClickListener.isInitialized)
                    onDeleteClickListener.onDeleteClick()
            }
            dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as? OnModifyClickListener)?.let {
            onModifyClickListener = it
        }
        (context as? OnDeleteClickListener)?.let {
            onDeleteClickListener = it
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