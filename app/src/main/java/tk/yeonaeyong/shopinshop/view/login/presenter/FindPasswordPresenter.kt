package tk.yeonaeyong.shopinshop.view.login.presenter

import tk.yeonaeyong.shopinshop.data.repository.member.MemberCallback
import tk.yeonaeyong.shopinshop.data.repository.member.MemberRepository
import tk.yeonaeyong.shopinshop.network.model.FindPasswordResponse

class FindPasswordPresenter(
    private val repository: MemberRepository,
    private val view: FindPasswordContract.View
) : FindPasswordContract.Presenter {
    override fun findPassword(email: String, phone: String) {
        repository.findPassword(email, phone, object :
            MemberCallback<FindPasswordResponse> {
            override fun onSuccess(response: FindPasswordResponse) {
                view.deliverUserInfo(response.emailCheck)
            }

            override fun onFailure(message: String) {
                view.showMessage(message)
            }

        })
    }
}