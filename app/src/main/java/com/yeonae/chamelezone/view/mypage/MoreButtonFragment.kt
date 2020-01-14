package com.yeonae.chamelezone.view.mypage

import android.app.Dialog
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yeonae.chamelezone.R

class MoreButtonFragment: BottomSheetDialogFragment(){
    override fun setupDialog(dialog: Dialog, style: Int) {
        if (dialog != null) {
            super.setupDialog(dialog, style)
        }
        val contentView = View.inflate(context, R.layout.fragment_more_button, null)
        dialog?.setContentView(contentView)
    }
}