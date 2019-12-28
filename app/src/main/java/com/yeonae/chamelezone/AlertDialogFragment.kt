package com.yeonae.chamelezone

import android.app.AlertDialog
import android.app.Dialog
import androidx.fragment.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle

class AlertDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val message = arguments!!.getString("message")

        return AlertDialog.Builder(activity)
            .setMessage(message)
            .setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, whichButton -> dialog.cancel() }
            )
            .create()
    }

    companion object {
        fun newInstance(message: String): AlertDialogFragment {
            val frag = AlertDialogFragment()
            val args = Bundle()
            args.putString("message", message)
            frag.arguments = args
            return frag
        }
    }
}
