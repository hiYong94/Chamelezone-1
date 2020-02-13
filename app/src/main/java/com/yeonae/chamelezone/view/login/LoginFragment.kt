package com.yeonae.chamelezone.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.yeonae.chamelezone.AlertDialogFragment
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.view.login.presenter.LoginContract
import com.yeonae.chamelezone.view.login.presenter.LoginPresenter
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(), LoginContract.View {
    override fun showDialog(message: String) {
        val newFragment = AlertDialogFragment.newInstance(
            message
        )
        fragmentManager?.let { newFragment.show(it, "dialog") }
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message + "환영합니다.", Toast.LENGTH_LONG)
            .show()
        (activity as? LoginActivity)?.finish()
    }

    override lateinit var presenter: LoginContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = LoginPresenter(
            Injection.memberRepository(requireContext()), this
        )

        btn_back.setOnClickListener {
            requireActivity().finish()
        }
        btn_find_email.setOnClickListener {
            (activity as? LoginActivity)?.replace(FindEmailFragment(), true)
        }
        btn_find_password.setOnClickListener {
            (activity as LoginActivity).replace(FindPasswordFragment(), true)
        }
        btn_join.setOnClickListener {
            (activity as LoginActivity).replace(JoinFragment(), true)
        }
        btn_login.setOnClickListener {
            loginCheck("${edt_email.text}", "${edt_password.text}")
        }

    }

    private fun loginCheck(email: String, password: String) {
        when {
            email.isEmpty() -> Toast.makeText(
                requireContext(),
                "아이디를 입력해주세요!",
                Toast.LENGTH_SHORT
            ).show()
            password.isEmpty() -> Toast.makeText(
                requireContext(),
                "비밀번호를 입력해주세요!",
                Toast.LENGTH_SHORT
            ).show()
            else -> {
                presenter.userLogin(email, password)
            }
        }
    }
}