package com.yeonae.chamelezone.view.login.presenter

import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.data.repository.member.MemberRepository
import com.yeonae.chamelezone.network.model.MemberResponse

class FindPasswordPresenter(
    private val repository: MemberRepository,
    private val view: FindPasswordContract.View
) : FindPasswordContract.Presenter {
    override fun findPassword(email: String, phone: String) {
        repository.findPassword(email, phone, object : MemberCallBack<MemberResponse> {
            override fun onSuccess(response: MemberResponse) {
                view.deliverUserInfo(response.memberNumber)
            }

            override fun onFailure(message: String) {
                view.showMessage(message)
            }

        })
    }
}