package com.yeonae.chamelezone.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yeonae.chamelezone.R
import kotlinx.android.synthetic.main.fragment_confirm_email.*

class ConfirmEmailFragment : Fragment() {

    private val userEmail = "email"
    fun newInstance(email: String) = ConfirmEmailFragment().apply {
        arguments = Bundle().apply {
            putString(userEmail, email)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_confirm_email, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val email = arguments!!.getString(userEmail)
        tv_email.text = email
        btn_find_password.setOnClickListener {
            (activity as LoginActivity).replace(FindPasswordFragment(), true)
        }

        btn_login.setOnClickListener {
            (activity as LoginActivity).replace(LoginFragment(), true)
        }

        btn_back.setOnClickListener {
            (activity as LoginActivity).back(this)
        }
    }
}