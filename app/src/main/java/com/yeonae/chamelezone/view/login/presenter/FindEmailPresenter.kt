package com.yeonae.chamelezone.view.login.presenter

import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.data.repository.member.MemberRepository
import com.yeonae.chamelezone.network.model.EmailResponse

class FindEmailPresenter(
    private val repository: MemberRepository,
    private val view: FindEmailContract.View
) : FindEmailContract.Presenter {
    override fun findEmail(name: String, phone: String) {
        repository.findEmail(name, phone, object : MemberCallBack<List<EmailResponse>> {
            override fun onSuccess(response: List<EmailResponse>) {
                val emails = arrayListOf<String>()
                for (i in response.indices) {
                    emails.add(response[i].email)
                }
                view.showUserInfo(emails)
            }

            override fun onFailure(message: String) {
                view.showMessage(message)
            }

        })
    }
}