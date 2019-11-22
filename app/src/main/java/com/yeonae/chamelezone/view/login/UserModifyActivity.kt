package com.yeonae.chamelezone.view.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.R
import kotlinx.android.synthetic.main.activity_user_modify.*

class UserModifyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_modify)

        btn_back.setOnClickListener {
            finish()
        }
    }
}