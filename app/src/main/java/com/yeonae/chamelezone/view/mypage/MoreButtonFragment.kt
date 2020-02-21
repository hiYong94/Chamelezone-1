package com.yeonae.chamelezone.view.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yeonae.chamelezone.R
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
        btn_modify.setOnClickListener {
            Toast.makeText(context, "수정", Toast.LENGTH_SHORT).show()

            targetFragment?.onActivityResult(targetRequestCode, BTN_EDIT, Intent())

            dismiss()
        }
        btn_delete.setOnClickListener {
            Toast.makeText(context, "삭제",Toast.LENGTH_SHORT).show()

            targetFragment?.onActivityResult(targetRequestCode, BTN_DELETE, Intent())

            dismiss()
        }

    }

    companion object{
        const val BTN_EDIT = 10
        const val BTN_DELETE = 20
    }
}