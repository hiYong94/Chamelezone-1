package tk.yeonaeyong.shopinshop.view.login.presenter

import tk.yeonaeyong.shopinshop.data.repository.member.MemberCallback
import tk.yeonaeyong.shopinshop.data.repository.member.MemberRepository
import tk.yeonaeyong.shopinshop.network.model.EmailResponse

class FindEmailPresenter(
    private val repository: MemberRepository,
    private val view: FindEmailContract.View
) : FindEmailContract.Presenter {
    override fun findEmail(name: String, phone: String) {
        repository.findEmail(name, phone, object :
            MemberCallback<List<EmailResponse>> {
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