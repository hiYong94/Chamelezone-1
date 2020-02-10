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
            override fun onSuccess(response: String) {
                joinView.showMessage(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun userLogin(email: String, password: String) {
        memberRepository.getMember(email, password, object : MemberCallBack<MemberResponse>{
            override fun onSuccess(response: MemberResponse) {
                joinView.showMessage(response.nickName + "환영합니다.")
            }

            override fun onFailure(message: String) {

            }

        }, object :MemberCallBack<Boolean>{
            override fun onSuccess(response: Boolean) {

            }

            override fun onFailure(message: String) {

            }

        })
    }
}