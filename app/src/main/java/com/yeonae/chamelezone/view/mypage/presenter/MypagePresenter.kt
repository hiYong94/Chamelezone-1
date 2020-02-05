package com.yeonae.chamelezone.view.mypage.presenter

import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.data.repository.member.MemberRepository

class MypagePresenter(
    private val repository: MemberRepository,
    private val view: MypageContract.View
) : MypageContract.Presenter {
    override fun logout() {
        repository.logout(object : MemberCallBack<String>{
            override fun onSuccess(response: String) {
                view.showMessage(response)
            }

            override fun onFailure(message: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    override fun checkLogin() {
        repository.checkLogin(object : MemberCallBack<Boolean>{
            override fun onSuccess(response: Boolean) {
                view.showLoginView(response)
            }

            override fun onFailure(message: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}