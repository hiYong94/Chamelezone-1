package com.yeonae.chamelezone.view.like.presenter

import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.data.repository.member.MemberRepository

class LikePresenter(
    private val repository: MemberRepository,
    private val view: LikeContract.View
) : LikeContract.Presenter {

    override fun checkLogin() {
        repository.checkLogin(object : MemberCallBack<Boolean> {
            override fun onSuccess(response: Boolean) {
                view.showResultView(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}