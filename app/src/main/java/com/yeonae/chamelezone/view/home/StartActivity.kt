package com.yeonae.chamelezone.view.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.view.Context.APPLICATION_CONTEXT
import com.yeonae.chamelezone.view.home.presenter.StartContract
import com.yeonae.chamelezone.view.home.presenter.StartPresenter

class StartActivity : AppCompatActivity(), StartContract.View {
    override fun showMessage(isSuccess: Boolean) {
        if (isSuccess) {
            val intent = Intent(applicationContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }
    }

    override lateinit var presenter: StartContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        presenter = StartPresenter(
            Injection.memberRepository(APPLICATION_CONTEXT), this
        )
        presenter.logout()

    }
}
