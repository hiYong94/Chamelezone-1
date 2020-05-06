package tk.yeonaeyong.shopinshop.view.login.presenter

import tk.yeonaeyong.shopinshop.data.repository.member.MemberCallback
import tk.yeonaeyong.shopinshop.data.repository.member.MemberRepository
import tk.yeonaeyong.shopinshop.network.model.EmailResponse
import tk.yeonaeyong.shopinshop.network.model.NicknameResponse

class JoinPresenter(
    private val memberRepository: MemberRepository,
    private val joinView: JoinContract.View
) : JoinContract.Presenter {
    override fun userRegister(
        email: String,
        password: String,
        name: String,
        nickName: String,
        phone: String
    ) {
        memberRepository.createMember(
            email,
            password,
            name,
            nickName,
            phone,
            object :
                MemberCallback<String> {
                override fun onSuccess(response: String) {
                    joinView.showMessage(response)
                }

                override fun onFailure(message: String) {

                }

            })
    }

    override fun checkEmail(email: String) {
        memberRepository.checkEmail(email, object :
            MemberCallback<EmailResponse> {
            override fun onSuccess(response: EmailResponse) {
                joinView.showEmailMessage(response.emailCheck)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun checkNickname(nickName: String) {
        memberRepository.checkNickname(nickName, object :
            MemberCallback<NicknameResponse> {
            override fun onSuccess(response: NicknameResponse) {
                joinView.showNicknameMessage(response.nicknameCheck)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}