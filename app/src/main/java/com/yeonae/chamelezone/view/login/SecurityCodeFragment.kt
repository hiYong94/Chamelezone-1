package com.yeonae.chamelezone.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yeonae.chamelezone.R
import kotlinx.android.synthetic.main.fragment_security_code.*

class SecurityCodeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_security_code, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val memberNumber = arguments?.getInt(MEMBER_NUMBER)

        btn_back.setOnClickListener {
            (activity as LoginActivity).back()
        }

        btn_security_code.setOnClickListener {

        }
    }

    companion object {
        private const val MEMBER_NUMBER = "memberNumber"
        fun newInstance(memberNumber: Int) = SecurityCodeFragment().apply {
            arguments = Bundle().apply {
                putInt(MEMBER_NUMBER, memberNumber)
            }
        }
    }
}