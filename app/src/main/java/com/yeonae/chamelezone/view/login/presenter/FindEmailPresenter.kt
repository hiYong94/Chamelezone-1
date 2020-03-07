package com.yeonae.chamelezone.view.login.presenter

import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.data.repository.member.MemberRepository
import com.yeonae.chamelezone.network.model.EmailResponse
import com.yeonae.chamelezone.network.model.MemberResponse

class FindEmailPresenter(
    private val repository: MemberRepository,
    private val view: FindEmailContract.View
) : FindEmailContract.Presenter {
    override fun findEmail(name: String, phone: String) {
        repository.findEmail(name, phone, object : MemberCallBack<List<EmailResponse>>{
            override fun onSuccess(response: List<EmailResponse>) {
                val emails = arrayListOf<String>()
                for (i in response.indices) {
                    emails[i] = response[i].email
                }
                view.showUserInfo(emails)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun findPassword(email: String, phone: String) {
        repository.findPassword(email, phone, object : MemberCallBack<MemberResponse>{
            override fun onSuccess(response: MemberResponse) {
                //view.showUserInfo(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}