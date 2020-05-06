package tk.yeonaeyong.shopinshop.view.login.presenter

import tk.yeonaeyong.shopinshop.data.repository.member.MemberCallback
import tk.yeonaeyong.shopinshop.data.repository.member.MemberRepository
import tk.yeonaeyong.shopinshop.network.model.SecurityCodeResponse

class SecurityCodePresenter(
    private val repository: MemberRepository,
    private val view: SecurityCodeContract.View
) : SecurityCodeContract.Presenter {
    override fun checkSecurityCode(securityCode: String, email: String, phone: String) {
        repository.checkSecurityCode(
            securityCode,
            email,
            phone,
            object :
                MemberCallback<SecurityCodeResponse> {
                override fun onSuccess(response: SecurityCodeResponse) {
                    view.showResultView(response.matchResult)
                }

                override fun onFailure(message: String) {
                    view.showDialog()
                }

            })
    }
}