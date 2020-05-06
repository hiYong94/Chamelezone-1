package tk.yeonaeyong.shopinshop.view.mypage.presenter

import tk.yeonaeyong.shopinshop.data.repository.member.MemberCallback
import tk.yeonaeyong.shopinshop.data.repository.member.MemberRepository
import tk.yeonaeyong.shopinshop.network.model.NicknameResponse
import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity

class UserModifyPresenter(
    private val repository: MemberRepository,
    private val view: UserModifyContract.View
) : UserModifyContract.Presenter {
    override fun getUser() {
        repository.getMember(object :
            MemberCallback<UserEntity> {
            override fun onSuccess(response: UserEntity) {
                view.showUserInfo(response)
            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun updateMember(
        memberNumber: Int,
        password: String?,
        nickName: String,
        phone: String
    ) {
        repository.updateMember(
            memberNumber,
            password,
            nickName,
            phone,
            object :
                MemberCallback<Boolean> {
                override fun onSuccess(response: Boolean) {
                    view.showMessage(response)
                }

                override fun onFailure(message: String) {

                }

            },
            object :
                MemberCallback<Boolean> {
                override fun onSuccess(response: Boolean) {

                }

                override fun onFailure(message: String) {

                }

            })
    }

    override fun checkNickname(nickName: String) {
        repository.checkNickname(nickName, object :
            MemberCallback<NicknameResponse> {
            override fun onSuccess(response: NicknameResponse) {
                view.showNicknameMessage(response.nicknameCheck)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}