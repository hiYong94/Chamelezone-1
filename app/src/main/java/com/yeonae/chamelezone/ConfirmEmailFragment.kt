package com.yeonae.chamelezone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_confirm_email.*

class ConfirmEmailFragment : Fragment() {
    fun newInstance(): ConfirmEmailFragment {
        return ConfirmEmailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val email = arguments?.getString("email")
        return inflater.inflate(R.layout.fragment_confirm_email, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_find_password.setOnClickListener {
            (activity as LoginActivity).replace(FindPasswordFragment())
        }

        btn_login.setOnClickListener {
            (activity as LoginActivity).replace(LoginFragment())
        }

        btn_back.setOnClickListener {
            (activity as LoginActivity).replace(FindEmailFragment())
        }
    }
}