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
import com.yeonae.chamelezone.network.model.EmailResponse
import com.yeonae.chamelezone.view.login.presenter.FindEmailContract
import com.yeonae.chamelezone.view.login.presenter.FindEmailPresenter
import kotlinx.android.synthetic.main.fragment_find_email.*

class FindEmailFragment : Fragment(), FindEmailContract.View {
    override lateinit var presenter: FindEmailContract.Presenter

    override fun showUserInfo(response: List<EmailResponse>) {
        val emailList = mutableListOf<String>()
        for(i in response.indices){
            emailList.add(response[i].email)
        }
        Log.d("email", emailList.toString())
        (activity as? LoginActivity)?.replace(
            ConfirmEmailFragment.newInstance(emailList),
            true
        )
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
            Injection.memberRepository(requireContext()), this
        )

        btn_find_email.setOnClickListener {
            presenter.findEmail("${edt_name.text}", "${edt_phone.text}")
        }

        btn_back.setOnClickListener {
            (activity as LoginActivity).back()
        }
    }

    private fun showDialog() {
        val newFragment = SingleDialogFragment.newInstance(
            R.string.information_not_exist
        )
        newFragment.show(fragmentManager!!, "dialog")
    }
}