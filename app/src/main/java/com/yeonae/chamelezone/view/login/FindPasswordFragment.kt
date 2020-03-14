package com.yeonae.chamelezone.view.login

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.SingleDialogFragment
import com.yeonae.chamelezone.ext.shortToast
import com.yeonae.chamelezone.network.model.EmailSendResultResponse
import com.yeonae.chamelezone.view.login.presenter.FindPasswordContract
import com.yeonae.chamelezone.view.login.presenter.FindPasswordPresenter
import kotlinx.android.synthetic.main.fragment_find_password.*

class FindPasswordFragment : Fragment(), FindPasswordContract.View {
    override fun deliverUserInfo(response: EmailSendResultResponse) {
        Log.d("pwResponse", response.memberNumber.toString())
        (activity as? LoginActivity)?.replace(
            SecurityCodeFragment.newInstance(
                response.memberNumber,
                "${edt_email.text}",
                "${edt_phone.text}"
            ),
            true
        )
    }

    override fun showMessage(message: String) {
        val newFragment = SingleDialogFragment.newInstance(
            R.string.information_not_exist
        )
        fragmentManager?.let { newFragment.show(it, "dialog") }
    }

    override lateinit var presenter: FindPasswordContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_find_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        edt_phone.inputType = android.text.InputType.TYPE_CLASS_PHONE
        edt_phone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        presenter = FindPasswordPresenter(
            Injection.memberRepository(), this
        )

        btn_find_password.setOnClickListener {
            when {
                "${edt_email.text}".isEmpty() -> context?.shortToast(R.string.enter_name)
                "${edt_phone.text}".isEmpty() -> context?.shortToast(R.string.enter_phone_number)
                else -> presenter.findPassword("${edt_email.text}", "${edt_phone.text}")
            }
        }

        btn_back.setOnClickListener {
            (activity as LoginActivity).back()
        }
    }
}