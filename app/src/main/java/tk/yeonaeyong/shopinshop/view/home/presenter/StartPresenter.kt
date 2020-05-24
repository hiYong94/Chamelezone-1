package tk.yeonaeyong.shopinshop.view.home.presenter

import tk.yeonaeyong.shopinshop.data.repository.member.MemberCallback
import tk.yeonaeyong.shopinshop.data.repository.member.MemberRepository
import tk.yeonaeyong.shopinshop.data.repository.version.VersionCallback
import tk.yeonaeyong.shopinshop.data.repository.version.VersionRepository
import tk.yeonaeyong.shopinshop.network.model.MemberResponse
import tk.yeonaeyong.shopinshop.network.model.VersionResponse

class StartPresenter(
    private val memberRepository: MemberRepository,
    private val versionRepository: VersionRepository,
    private val view: StartContract.View
) : StartContract.Presenter {
    override fun userLogin(email: String, password: String) {
        memberRepository.login(email, password, object :
            MemberCallback<MemberResponse> {
            override fun onSuccess(response: MemberResponse) {
                view.showMessage(true)
            }

            override fun onFailure(message: String) {
                view.showDialog(message)
            }

        }, object :
            MemberCallback<Boolean> {
            override fun onSuccess(response: Boolean) {

            }

            override fun onFailure(message: String) {

            }

        })
    }

    override fun logout() {
        memberRepository.deleteLoginUser(object :
            MemberCallback<Boolean> {
            override fun onSuccess(response: Boolean) {
                view.showMessage(response)
            }

            override fun onFailure(message: String) {

            }
        })
    }

    override fun getAppVersion() {
        versionRepository.getAppVersion(object : VersionCallback<VersionResponse> {
            override fun onSuccess(response: VersionResponse) {
                view.showAppUpdateDialog(response.version)
            }

            override fun onFailure(message: String) {

            }

        })
    }
}