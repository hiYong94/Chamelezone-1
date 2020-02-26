package com.yeonae.chamelezone

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_check_dialog.*
import kotlinx.android.synthetic.main.fragment_multiple_dialog.*
import kotlinx.android.synthetic.main.fragment_multiple_dialog.btn_cancel
import kotlinx.android.synthetic.main.fragment_multiple_dialog.btn_ok

class CheckDialogFragment : DialogFragment() {
    private var heightRatio = 0.8803125f  // default
    private var widthRatio = 0.88888889f // default

    private var onClickListener: OnClickListener? = null

    interface OnClickListener {
        fun onClick()
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

        val keyword = arguments?.getStringArray("keyword")
        val layout = LayoutInflater.from(context)
            .inflate(R.layout.item_check_box, check_box, false)
        if (keyword != null) {
            for(i in keyword.indices){
                check_box.addView(layout)
                layout.findViewById<View>(R.id.keyword)
            }
        }

        btn_ok.setOnClickListener {
            onClickListener?.onClick()
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

    companion object {
        const val DIALOG_HEIGHT_RATIO = "DIALOG_HEIGHT_RATIO"
        const val DIALOG_WIDTH_RATIO = "DIALOG_WIDTH_RATIO"
        fun newInstance(items: Array<String>, listener: OnClickListener): CheckDialogFragment {
            val frag = CheckDialogFragment()
            val args = Bundle()
            args.putStringArray("keyword", items)
            frag.arguments = args
            frag.onClickListener = listener
            return frag
        }
    }
}
