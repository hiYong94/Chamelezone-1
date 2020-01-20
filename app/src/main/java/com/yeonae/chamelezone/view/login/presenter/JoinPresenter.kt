package com.yeonae.chamelezone.view.login.presenter

import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.data.repository.member.MemberRepository
import com.yeonae.chamelezone.network.model.MemberResponse

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
        memberRepository.createMember(email, password, name, nickName, phone, object : MemberCallBack<String>{
            override fun onSuccess(message: String) {
                joinView.showMessage(message)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun userLogin(email: String, password: String) {
        memberRepository.login(email, password, object : MemberCallBack<MemberResponse>{
            override fun onSuccess(response: MemberResponse) {
                joinView.showMessage(response.nickName + "환영합니다.")
            }

            override fun onFailure(message: String) {

            }

        })
    }
}