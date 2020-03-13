package com.yeonae.chamelezone.view.login

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.SingleDialogFragment
import com.yeonae.chamelezone.ext.shortToast
import com.yeonae.chamelezone.view.login.presenter.FindEmailContract
import com.yeonae.chamelezone.view.login.presenter.FindEmailPresenter
import kotlinx.android.synthetic.main.fragment_find_email.*

class FindEmailFragment : Fragment(), FindEmailContract.View {
    override lateinit var presenter: FindEmailContract.Presenter

    override fun showUserInfo(emails: ArrayList<String>) {
        (activity as? LoginActivity)?.replace(
            ConfirmEmailFragment.newInstance(emails),
            true
        )
    }

    override fun showMessage(message: String) {
        val newFragment = SingleDialogFragment.newInstance(
            R.string.information_not_exist
        )
        fragmentManager?.let { newFragment.show(it, "dialog") }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_find_email, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        edt_phone.inputType = android.text.InputType.TYPE_CLASS_PHONE
        edt_phone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        presenter = FindEmailPresenter(
            Injection.memberRepository(), this
        )

        btn_find_email.setOnClickListener {
            when {
                "${edt_name.text}".isEmpty() -> context?.shortToast(R.string.enter_name)
                "${edt_phone.text}".isEmpty() -> context?.shortToast(R.string.enter_phone_number)
                else -> presenter.findEmail("${edt_name.text}", "${edt_phone.text}")
            }
        }

        btn_back.setOnClickListener {
            (activity as? LoginActivity)?.back()
        }
    }
}