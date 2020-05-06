package tk.yeonaeyong.shopinshop.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_security_code.*
import tk.yeonaeyong.shopinshop.Injection
import tk.yeonaeyong.shopinshop.R
import tk.yeonaeyong.shopinshop.SingleDialogFragment
import tk.yeonaeyong.shopinshop.view.login.presenter.SecurityCodeContract
import tk.yeonaeyong.shopinshop.view.login.presenter.SecurityCodePresenter

class SecurityCodeFragment : Fragment(), SecurityCodeContract.View {
    override lateinit var presenter: SecurityCodeContract.Presenter
    var memberNumber = 0

    override fun showResultView(matchResult: Boolean) {
        if (matchResult) {
            (activity as? LoginActivity)?.back()
            (activity as? LoginActivity)?.replace(
                ChangePasswordFragment.newInstance(memberNumber),
                true
            )
        }
    }

    override fun showDialog() {
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
        return inflater.inflate(R.layout.fragment_security_code, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        memberNumber = arguments?.getInt(MEMBER_NUMBER) ?: 0
        val email = arguments?.getString(EMAIL)
        val phone = arguments?.getString(PHONE)
        presenter = SecurityCodePresenter(
            Injection.memberRepository(), this
        )

        btn_back.setOnClickListener {
            (activity as LoginActivity).back()
        }

        btn_security_code.setOnClickListener {
            if (email != null && phone != null) {
                presenter.checkSecurityCode("${edt_security_code.text}", email, phone)
            }
        }
    }

    companion object {
        private const val MEMBER_NUMBER = "memberNumber"
        private const val EMAIL = "email"
        private const val PHONE = "phoneNumber"
        fun newInstance(memberNumber: Int, email: String, phone: String) =
            SecurityCodeFragment().apply {
                arguments = Bundle().apply {
                    putInt(MEMBER_NUMBER, memberNumber)
                    putString(EMAIL, email)
                    putString(PHONE, phone)
                }
            }
    }
}