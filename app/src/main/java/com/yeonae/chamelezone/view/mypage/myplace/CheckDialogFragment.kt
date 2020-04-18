package com.yeonae.chamelezone.view.mypage.myplace

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.DialogFragment
import com.yeonae.chamelezone.R
import kotlinx.android.synthetic.main.fragment_check_dialog.*
import kotlinx.android.synthetic.main.fragment_multiple_dialog.btn_cancel
import kotlinx.android.synthetic.main.fragment_multiple_dialog.btn_ok

class CheckDialogFragment : DialogFragment() {
    private var heightRatio = 0.8803125f  // default
    private var widthRatio = 0.88888889f // default
    private var selectedKeyword = arrayListOf<String>()
    private var onClickListener: OnClickListener? = null

    interface OnClickListener {
        fun onClick(keywordList: ArrayList<String>)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as? OnClickListener).let {
            onClickListener = it
        }
    }

    override fun onStart() {
        super.onStart()
        val dpMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(dpMetrics)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        arguments?.getFloat(DIALOG_HEIGHT_RATIO)?.let {
            if (it > 0) {
                heightRatio = it
            }
        }
        arguments?.getFloat(DIALOG_WIDTH_RATIO)?.let {
            if (it > 0) {
                widthRatio = it
            }
        }
        dialog?.window?.setLayout(
            (dpMetrics.widthPixels * widthRatio).toInt(),
            (dpMetrics.heightPixels * heightRatio).toInt()
        )

        val keyword = arguments?.getStringArrayList("keyword")
        selectedKeyword = arguments?.getStringArrayList("selectedKeyword") as ArrayList<String>

        if (keyword != null) {
            for (i in 0 until keyword.size) {
                addKeywordView(keyword[i])
            }
        }

        btn_ok.setOnClickListener {
            onClickListener?.onClick(selectedKeyword)
            dialog?.cancel()
        }

        btn_cancel.setOnClickListener {
            dialog?.cancel()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_check_dialog, container, false)
    }

    private fun addKeywordView(keyword: String) {
        val layout = LayoutInflater.from(context)
            .inflate(R.layout.item_check_box, check_box, false)
        check_box.addView(layout)
        layout.findViewById<CheckBox>(R.id.cb_keyword).run {
            this.text = keyword
            selectedKeyword.forEach {
                if (it == keyword) {
                    this.isChecked = true
                }
            }
            setOnClickListener {
                if (this.isChecked) {
                    selectedKeyword.add(this.text.toString())
                } else {
                    selectedKeyword.remove(this.text.toString())
                }
            }
        }
    }

    companion object {
        const val DIALOG_HEIGHT_RATIO = "DIALOG_HEIGHT_RATIO"
        const val DIALOG_WIDTH_RATIO = "DIALOG_WIDTH_RATIO"
        fun newInstance(
            keywordList: ArrayList<String>,
            selectedKeyword: ArrayList<String>
        ): CheckDialogFragment {
            return CheckDialogFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList("keyword", keywordList)
                    putStringArrayList("selectedKeyword", selectedKeyword)
                }
            }
        }
    }
}
