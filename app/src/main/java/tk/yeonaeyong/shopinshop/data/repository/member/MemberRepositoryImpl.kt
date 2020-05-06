package tk.yeonaeyong.shopinshop.data.repository.member

import tk.yeonaeyong.shopinshop.data.source.local.member.MemberLocalDataSource
import tk.yeonaeyong.shopinshop.data.source.remote.member.MemberRemoteDataSource
import tk.yeonaeyong.shopinshop.network.model.*
import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity

class MemberRepositoryImpl private constructor(
    private val remoteDataSource: MemberRemoteDataSource,
    private val localDataSource: MemberLocalDataSource
) :
    MemberRepository {
    override fun createMember(
        email: String,
        password: String,
        name: String,
        nickName: String,
        phone: String,
        callback: MemberCallback<String>
    ) {
        remoteDataSource.createMember(email, password, name, nickName, phone, callback)
    }

    override fun getMember(callback: MemberCallback<UserEntity>) {
        localDataSource.getMember(callback)
    }

    override fun login(
        email: String,
        password: String,
        callback: MemberCallback<MemberResponse>,
        localCallback: MemberCallback<Boolean>
    ) {
        remoteDataSource.login(email, password, object :
            MemberCallback<MemberResponse> {
            override fun onSuccess(response: MemberResponse) {
                callback.onSuccess(response)
                localDataSource.loggedLogin(response, localCallback)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }
        })
    }

    override fun logout(callback: MemberCallback<String>) {
        localDataSource.logout(callback)
    }

    override fun updateMember(
        memberNumber: Int,
        password: String?,
        nickName: String,
        phone: String,
        callback: MemberCallback<Boolean>,
        localCallback: MemberCallback<Boolean>
    ) {
        remoteDataSource.updateMember(
            memberNumber,
            password,
            nickName,
            phone,
            object :
                MemberCallback<Boolean> {
                override fun onSuccess(response: Boolean) {
                    callback.onSuccess(response)
                    if (response) {
                        localDataSource.updateMember(nickName, phone, localCallback)
                    }
                }

                override fun onFailure(message: String) {
                    callback.onFailure(message)
                }

            })
    }

    override fun deleteMember(memberNumber: Int, callback: MemberCallback<String>) {
        remoteDataSource.deleteMember(memberNumber, callback)
    }

    override fun checkLogin(callback: MemberCallback<Boolean>) {
        localDataSource.isLogged(callback)
    }

    override fun deleteLoginUser(callback: MemberCallback<Boolean>) {
        localDataSource.deleteAll(callback)
    }

    override fun checkEmail(email: String, callback: MemberCallback<EmailResponse>) {
        remoteDataSource.checkEmail(email, callback)
    }

    override fun checkNickname(nickname: String, callback: MemberCallback<NicknameResponse>) {
        remoteDataSource.checkNickname(nickname, callback)
    }

    override fun findEmail(
        name: String,
        phone: String,
        callback: MemberCallback<List<EmailResponse>>
    ) {
        remoteDataSource.findEmail(name, phone, callback)
    }

    override fun findPassword(
        email: String,
        phone: String,
        callback: MemberCallback<FindPasswordResponse>
    ) {
        remoteDataSource.findPassword(email, phone, callback)
    }

    override fun checkSecurityCode(
        securityCode: String,
        email: String,
        phone: String,
        callback: MemberCallback<SecurityCodeResponse>
    ) {
        remoteDataSource.checkSecurityCode(securityCode, email, phone, callback)
    }

    override fun changePassword(
        password: String,
        memberNumber: Int,
        callback: MemberCallback<Boolean>
    ) {
        remoteDataSource.changePassword(password, memberNumber, callback)
    }

    companion object {
        fun getInstance(
            remoteDataSource: MemberRemoteDataSource,
            localDataSource: MemberLocalDataSource
        ): MemberRepository =
            MemberRepositoryImpl(
                remoteDataSource,
                localDataSource
            )
    }
}