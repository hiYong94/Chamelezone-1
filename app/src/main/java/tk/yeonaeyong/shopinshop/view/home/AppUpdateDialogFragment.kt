package tk.yeonaeyong.shopinshop.view.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_multiple_dialog.*
import tk.yeonaeyong.shopinshop.R

class AppUpdateDialogFragment : DialogFragment() {
    private var heightRatio = 0.2403125f  // default
    private var widthRatio = 0.88888889f // default

    private var onClickListener: OnClickListener? = null

    interface OnClickListener {
        fun onOkClick()
        fun onCancelClick()
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

        val message = arguments?.getString("message")
        tv_message.text = message

        btn_ok.setOnClickListener {
            onClickListener?.onOkClick()
            dialog?.cancel()
        }

        btn_cancel.setOnClickListener {
            onClickListener?.onCancelClick()
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
        fun newInstance(message: String, listener: OnClickListener): AppUpdateDialogFragment {
            val frag = AppUpdateDialogFragment()
            val args = Bundle()
            args.putString("message", message)
            frag.arguments = args
            frag.onClickListener = listener
            return frag
        }
    }
}
