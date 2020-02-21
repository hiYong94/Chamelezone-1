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
        memberRepository.createMember(
            email,
            password,
            name,
            nickName,
            phone,
            object : MemberCallBack<String> {
                override fun onSuccess(response: String) {
                    joinView.showMessage(response)
                }

                override fun onFailure(message: String) {

                }

            })
    }

    override fun checkEmail(email: String) {
        memberRepository.checkEmail(email, object : MemberCallBack<Boolean>{
            override fun onSuccess(response: Boolean) {
                joinView.showEmailMessage(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun checkNickname(nickName: String) {
        memberRepository.checkNickname(nickName, object : MemberCallBack<Boolean>{
            override fun onSuccess(response: Boolean) {
                joinView.showNicknameMessage(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}