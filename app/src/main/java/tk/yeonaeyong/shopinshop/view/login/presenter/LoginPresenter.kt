package tk.yeonaeyong.shopinshop.view.login.presenter

import tk.yeonaeyong.shopinshop.data.repository.member.MemberCallback
import tk.yeonaeyong.shopinshop.data.repository.member.MemberRepository
import tk.yeonaeyong.shopinshop.network.model.MemberResponse

class LoginPresenter(
    private val memberRepository: MemberRepository,
    private val joinView: LoginContract.View
) : LoginContract.Presenter {
    override fun userLogin(email: String, password: String) {
        memberRepository.login(email, password, object :
            MemberCallback<MemberResponse> {
            override fun onSuccess(response: MemberResponse) {
                joinView.showMessage(response.nickName)
            }

            override fun onFailure(message: String) {
                joinView.showDialog(message)
            }

        }, object :
            MemberCallback<Boolean> {
            override fun onSuccess(response: Boolean) {

            }

            override fun onFailure(message: String) {

            }

        })
    }
}