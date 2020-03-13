package com.yeonae.chamelezone.view.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.SingleDialogFragment
import com.yeonae.chamelezone.view.home.presenter.StartContract
import com.yeonae.chamelezone.view.home.presenter.StartPresenter

class StartActivity : AppCompatActivity(), StartContract.View {
    override fun showDialog(message: String) {
        val newFragment = SingleDialogFragment.newInstance(R.string.auto_login_fail)
        newFragment.show(supportFragmentManager, "dialog")
    }

    override fun showMessage(isSuccess: Boolean) {
        if (isSuccess) {
            startHomeActivity()
        }
    }

    private fun startHomeActivity() {
        val intent = Intent(applicationContext, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
        finish()
    }

    override lateinit var presenter: StartContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        presenter = StartPresenter(
            Injection.memberRepository(), this
        )
        val setting = getSharedPreferences("setting", 0)
        val email = setting.getString("ID", "")
        val password = setting.getString("PW", "")
        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            presenter.userLogin(email, password)
        } else {
            presenter.logout()
        }
    }
}
