package com.yeonae.chamelezone.view.home.presenter

import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.data.repository.member.MemberRepository

class StartPresenter(
    private val repository: MemberRepository,
    private val view: StartContract.View
) : StartContract.Presenter {
    override fun logout() {
        repository.deleteLoginUser(object : MemberCallBack<Boolean>{
            override fun onSuccess(response: Boolean) {
                view.showMessage(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }
}