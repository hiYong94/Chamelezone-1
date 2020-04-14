package com.yeonae.chamelezone.view.mypage.presenter

import com.yeonae.chamelezone.data.repository.member.MemberCallback
import com.yeonae.chamelezone.data.repository.member.MemberRepository
import com.yeonae.chamelezone.network.room.entity.UserEntity

class MypagePresenter(
    private val repository: MemberRepository,
    private val view: MypageContract.View
) : MypageContract.Presenter {
    override fun logout() {
        repository.logout(object : MemberCallback<String>{
            override fun onSuccess(response: String) {

            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun checkLogin() {
        repository.checkLogin(object : MemberCallback<Boolean>{
            override fun onSuccess(response: Boolean) {
                view.showResultView(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun getUser() {
        repository.getMember(object : MemberCallback<UserEntity>{
            override fun onSuccess(response: UserEntity) {
                view.showUserInfo(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun deleteUser(memberNumber: Int) {
        repository.deleteMember(memberNumber, object : MemberCallback<String>{
            override fun onSuccess(response: String) {
                view.showMessage(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}