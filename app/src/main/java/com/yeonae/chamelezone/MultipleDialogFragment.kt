package com.yeonae.chamelezone

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_multiple_dialog.*

class MultipleDialogFragment : DialogFragment() {
    private var heightRatio = 0.2403125f  // default
    private var widthRatio = 0.88888889f // default

    private var onClickListener: OnClickListener? = null

    interface OnClickListener {
        fun onClick()
    }

//    fun setOnClickListener(listener: OnClickListener) {
//        onClickListener = listener
//    }

    override fun onStart() {
        super.onStart()
        val message = arguments!!.getString("message")
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

        tv_message.text = message

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
        return inflater.inflate(R.layout.fragment_multiple_dialog, container, false)
    }

    companion object {
        const val DIALOG_HEIGHT_RATIO = "DIALOG_HEIGHT_RATIO"
        const val DIALOG_WIDTH_RATIO = "DIALOG_WIDTH_RATIO"
        fun newInstance(message: String, listener: OnClickListener): MultipleDialogFragment {
            val frag = MultipleDialogFragment()
            val args = Bundle()
            args.putString("message", message)
            frag.arguments = args
            frag.onClickListener = listener
            return frag
        }
    }
}
