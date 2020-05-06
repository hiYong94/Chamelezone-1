package tk.yeonaeyong.shopinshop.view.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_login.*
import tk.yeonaeyong.shopinshop.Injection
import tk.yeonaeyong.shopinshop.R
import tk.yeonaeyong.shopinshop.SingleDialogFragment
import tk.yeonaeyong.shopinshop.view.login.presenter.LoginContract
import tk.yeonaeyong.shopinshop.view.login.presenter.LoginPresenter

class LoginFragment : Fragment(), LoginContract.View {
    override lateinit var presenter: LoginContract.Presenter

    override fun showDialog(message: String) {
        val newFragment = SingleDialogFragment.newInstance(
            R.string.check_email_password_again
        )
        fragmentManager?.let { newFragment.show(it, "dialog") }
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, "$message 환영합니다.", Toast.LENGTH_LONG)
            .show()
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(edt_email.windowToken, 0)
        imm.hideSoftInputFromWindow(edt_password.windowToken, 0)

        val editor = context?.getSharedPreferences("setting", 0)?.edit()
        if (cb_login.isChecked) {
            editor?.putString("ID", "${edt_email.text}")
            editor?.putString("PW", "${edt_password.text}")
            editor?.apply()
        } else {
            editor?.clear()
            editor?.apply()
        }
        (activity as? LoginActivity)?.finish()
    }

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
            Injection.memberRepository(), this
        )

        btn_back.setOnClickListener {
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(edt_email.windowToken, 0)
            imm.hideSoftInputFromWindow(edt_password.windowToken, 0)
            requireActivity().finish()
        }
        btn_find_email.setOnClickListener {
            (activity as? LoginActivity)?.replace(FindEmailFragment(), true)
        }
        btn_find_password.setOnClickListener {
            (activity as? LoginActivity)?.replace(FindPasswordFragment(), true)
        }
        btn_join.setOnClickListener {
            val intent = Intent(requireContext(), JoinActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
        btn_login.setOnClickListener {
            loginCheck("${edt_email.text}", "${edt_password.text}")
        }

    }

    private fun loginCheck(email: String, password: String) {
        when {
            email.isEmpty() -> Toast.makeText(
                requireContext(),
                R.string.enter_email,
                Toast.LENGTH_SHORT
            ).show()
            password.isEmpty() -> Toast.makeText(
                requireContext(),
                R.string.enter_password,
                Toast.LENGTH_SHORT
            ).show()
            else -> {
                presenter.userLogin(email, password)
            }
        }
    }
}