package com.yeonae.chamelezone.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yeonae.chamelezone.R
import kotlinx.android.synthetic.main.fragment_confirm_email.*

class ConfirmEmailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_confirm_email, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val emailList = arguments?.getStringArray(EMAIL).orEmpty()
        for(i in emailList.indices){
            tv_email.text = "${tv_email.text}" + "\n" + emailList[i]
        }

        btn_find_password.setOnClickListener {
            (activity as LoginActivity).replace(FindPasswordFragment(), true)
        }

        btn_login.setOnClickListener {
            (activity as LoginActivity).replace(LoginFragment(), true)
        }

        btn_back.setOnClickListener {
            (activity as LoginActivity).back()
        }
    }

    companion object {
        private const val EMAIL = "email"
        fun newInstance(emails: Array<String?>) = ConfirmEmailFragment().apply {
            arguments = Bundle().apply {
                putStringArray(EMAIL, emails)
            }
        }
    }
}