package com.yeonae.chamelezone.view.home.presenter

import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.data.repository.member.MemberRepository
import com.yeonae.chamelezone.network.model.MemberResponse

class StartPresenter(
    private val repository: MemberRepository,
    private val view: StartContract.View
) : StartContract.Presenter {
    override fun userLogin(email: String, password: String) {
        repository.login(email, password, object : MemberCallBack<MemberResponse> {
            override fun onSuccess(response: MemberResponse) {
                view.showMessage(true)
            }

            override fun onFailure(message: String) {
                view.showDialog(message)
            }

        }, object : MemberCallBack<Boolean> {
            override fun onSuccess(response: Boolean) {

            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun logout() {
        repository.deleteLoginUser(object : MemberCallBack<Boolean> {
            override fun onSuccess(response: Boolean) {
                view.showMessage(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }
}