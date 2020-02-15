package com.yeonae.chamelezone.view.login.presenter

import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.data.repository.member.MemberRepository
import com.yeonae.chamelezone.network.model.MemberResponse

class LoginPresenter(
    private val memberRepository: MemberRepository,
    private val joinView: LoginContract.View
) : LoginContract.Presenter {
    override fun userLogin(email: String, password: String) {
        memberRepository.login(email, password, object : MemberCallBack<MemberResponse> {
            override fun onSuccess(response: MemberResponse) {
                joinView.showMessage(response.nickName)
            }

            override fun onFailure(message: String) {
                joinView.showDialog(message)
            }

        }, object : MemberCallBack<Boolean> {
            override fun onSuccess(response: Boolean) {

            }

            override fun onFailure(message: String) {

            }

        })
    }
}