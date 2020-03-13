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
import kotlinx.android.synthetic.main.fragment_more_button.*


class MoreButtonFragment : BottomSheetDialogFragment() {
    private lateinit var deletedButtonListener: OnDeletedSelectedListener
    private val reviewNumber = arguments?.getInt(REVIEW_NUMBER)
    private val placeNumber = arguments?.getInt(PLACE_NUMBER)
    private val myReviewData = Intent().apply {
        putExtra(REVIEW_NUMBER, reviewNumber)
        putExtra(PLACE_NUMBER, placeNumber)
        Log.d("More reviewNumber", reviewNumber.toString())
    }

    interface OnDeletedSelectedListener {
        fun onDeleteSelected(intent: Intent)
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
            Toast.makeText(context, "수정", Toast.LENGTH_SHORT).show()

            targetFragment?.onActivityResult(targetRequestCode, BTN_EDIT, Intent())

            dismiss()
        }
        btn_delete.setOnClickListener {
            Toast.makeText(context, "삭제", Toast.LENGTH_SHORT).show()

            targetFragment?.onActivityResult(targetRequestCode, BTN_DELETE, data)

            if (::deletedButtonListener.isInitialized)
                deletedButtonListener.onDeleteSelected(myReviewData)

            dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (context as? OnDeletedSelectedListener)?.let {
            deletedButtonListener = it
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