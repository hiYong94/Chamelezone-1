package com.yeonae.chamelezone.view.login.presenter

import com.yeonae.chamelezone.data.repository.member.MemberCallback
import com.yeonae.chamelezone.data.repository.member.MemberRepository

class ChangePasswordPresenter(
    private val repository: MemberRepository,
    private val view: ChangePasswordContract.View
) : ChangePasswordContract.Presenter {
    override fun changePassword(password: String, memberNumber: Int) {
        repository.changePassword(password, memberNumber, object : MemberCallback<Boolean> {
            override fun onSuccess(response: Boolean) {
                view.showResultView(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}