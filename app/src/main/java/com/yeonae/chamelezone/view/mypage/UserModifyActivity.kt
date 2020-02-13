package com.yeonae.chamelezone.view.mypage

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.network.room.entity.UserEntity
import com.yeonae.chamelezone.view.mypage.presenter.UserModifyContract
import com.yeonae.chamelezone.view.mypage.presenter.UserModifyPresenter
import kotlinx.android.synthetic.main.activity_user_modify.*

class UserModifyActivity() : AppCompatActivity(), UserModifyContract.View {
    var memberNumber: Int = 0
    override fun showUserInfo(user: UserEntity) {
        val nickname = SpannableStringBuilder(user.nickname)
        val phone = SpannableStringBuilder(user.phone)
        user_email.hint = user.email
        user_name.hint = user.name
        user_nickname.text = nickname
        user_phone.text = phone
        memberNumber = user.userNumber ?: 0
    }

    override fun showMessage(message: String) {

    }

    override lateinit var presenter: UserModifyContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_modify)
        user_phone.inputType = android.text.InputType.TYPE_CLASS_PHONE
        user_phone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        presenter = UserModifyPresenter(
            Injection.memberRepository(applicationContext), this
        )

        presenter.getUser()

        btn_back.setOnClickListener {
            finish()
        }

        btn_user_modify.setOnClickListener {
            Log.d("memberNumber", memberNumber.toString())
            presenter.updateMember(
                memberNumber,
                "${user_password.text}",
                "${user_nickname.text}",
                "${user_phone.text}"
            )
        }
    }
}