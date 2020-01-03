package com.yeonae.chamelezone.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.yeonae.chamelezone.AlertDialogFragment
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.repository.member.MemberRepositoryImpl
import com.yeonae.chamelezone.data.source.remote.member.MemberRemoteDataSourceImpl
import com.yeonae.chamelezone.network.api.RetrofitConnection
import com.yeonae.chamelezone.view.login.presenter.JoinContract
import com.yeonae.chamelezone.view.login.presenter.JoinPresenter
import kotlinx.android.synthetic.main.fragment_join.*
import java.util.regex.Pattern

class JoinFragment : Fragment(), JoinContract.View {
    override fun join(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG)
            .show()
    }

    val testEmail = "heimish_08@naver.com"
    val testNickname = "yeonvely"
    private val retrofitConnection = RetrofitConnection
    override lateinit var presenter: JoinContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_join, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = JoinPresenter(
            MemberRepositoryImpl.getInstance(
                MemberRemoteDataSourceImpl.getInstance(retrofitConnection)
            ), this
        )

        join_email.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View?, hasFocus: Boolean) {
                if (hasFocus) {
                    val p =
                        Pattern.compile("^[a-zA-Z0-9_]+[@]+[a-zA-Z]+[.]+[a-zA-Z]+")// 정규식 변수 이름 바꾸기
                    val m = p.matcher(join_email.text.toString())

                    if (!m.matches()) {
                        Toast.makeText(
                            context!!.applicationContext,
                            "이메일 형식이 올바르지 않습니다.",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        if (testEmail == "${join_email.text}") {
                            Toast.makeText(
                                context!!.applicationContext,
                                "이미 존재하는 이메일 입니다.",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            Toast.makeText(
                                context!!.applicationContext,
                                "사용 가능한 이메일 입니다.",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
            }
        })

        join_password.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View?, hasFocus: Boolean) {
                if (hasFocus) {
                    val p = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[0-9]).{8,16}")
                    val m = p.matcher(join_password.text.toString())

                    if (!m.matches()) {
                        Toast.makeText(
                            context!!.applicationContext,
                            "영문, 숫자 포함 8~16자로 입력해주세요.",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        })

        join_nickname.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View?, hasFocus: Boolean) {
                if (hasFocus) {
                    val p = Pattern.compile("^[a-zA-Z0-9ㄱ-ㅎ가-힣]{1,20}")
                    val m = p.matcher(join_nickname.text.toString())

                    if (!m.matches()) {
                        Toast.makeText(
                            context!!.applicationContext,
                            "한글, 영문, 숫자 포함 1~20자로 입력해주세요.",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        if (testNickname == "${join_nickname.text}") {
                            Toast.makeText(
                                context!!.applicationContext,
                                "이미 존재하는 닉네임 입니다.",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            Toast.makeText(
                                context!!.applicationContext,
                                "사용 가능한 닉네임 입니다.",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
            }
        })

        btn_back.setOnClickListener {
            (activity as LoginActivity).back(this)
        }

        btn_join.setOnClickListener {
            when {
                "${join_email.text}".isEmpty() -> showDialog("아이디를 입력해주세요.")
                "${join_password.text}".isEmpty() -> showDialog("비밀번호를 입력해주세요.")
                "${join_name.text}".isEmpty() -> showDialog("이름을 입력해주세요.")
                "${join_nickname.text}".isEmpty() -> showDialog("닉네임을 입력해주세요.")
                "${join_phone.text}".isEmpty() -> showDialog("핸드폰 번호를 입력해주세요.")
                else -> presenter.userRegister(
                    "${join_email.text}",
                    "${join_password.text}",
                    "${join_name.text}",
                    "${join_nickname.text}",
                    "${join_phone.text}"
                )
            }
        }
    }

    private fun showDialog(message: String) {
        val newFragment = AlertDialogFragment.newInstance(
            message
        )
        newFragment.show(fragmentManager!!, "dialog")
    }
}