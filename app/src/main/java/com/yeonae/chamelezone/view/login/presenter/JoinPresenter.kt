package com.yeonae.chamelezone.view.login.presenter

import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.data.repository.member.MemberRepository

class JoinPresenter(
    private val memberRepository: MemberRepository,
    private val joinView: JoinContract.View
) : JoinContract.Presenter {
    override fun userRegister(
        email: String,
        password: String,
        name: String,
        nickName: String,
        phone: String
    ) {
        memberRepository.createMember(email, password, name, nickName, phone, object : MemberCallBack{
            override fun onSuccess(message: String) {
                joinView.join(message)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}