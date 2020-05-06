package tk.yeonaeyong.shopinshop.data.source.remote.member

import android.util.Log
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tk.yeonaeyong.shopinshop.App
import tk.yeonaeyong.shopinshop.R
import tk.yeonaeyong.shopinshop.data.Network.REQUEST_ERR
import tk.yeonaeyong.shopinshop.data.Network.SUCCESS
import tk.yeonaeyong.shopinshop.data.repository.member.MemberCallback
import tk.yeonaeyong.shopinshop.network.api.MemberApi
import tk.yeonaeyong.shopinshop.network.api.RetrofitConnection.memberService
import tk.yeonaeyong.shopinshop.network.model.*

class MemberRemoteDataSourceImpl private constructor(private val memberApi: MemberApi) :
    MemberRemoteDataSource {
    override fun createMember(
        email: String,
        password: String,
        name: String,
        nickName: String,
        phone: String,
        callback: MemberCallback<String>
    ) {
        val requestBody = JsonObject().apply {
            addProperty(EMAIL, email)
            addProperty(PASSWORD, password)
            addProperty(NAME, name)
            addProperty(NICKNAME, nickName)
            addProperty(PHONE, phone)
        }

        memberService.createMember(requestBody).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.code() == SUCCESS) {
                    callback.onSuccess(
                        App.instance.context().getString(R.string.success_register_member)
                    )
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("tag", t.toString())
            }
        })

    }

    override fun login(
        email: String,
        password: String,
        callback: MemberCallback<MemberResponse>
    ) {
        val requestBody = JsonObject().apply {
            addProperty(EMAIL, email)
            addProperty(PASSWORD, password)
        }

        memberService.getMember(requestBody).enqueue(object :
            Callback<MemberResponse> {
            override fun onResponse(
                call: Call<MemberResponse>,
                response: Response<MemberResponse>
            ) {
                if (response.code() == SUCCESS) {
                    response.body()?.let { callback.onSuccess(it) }
                } else if (response.code() == REQUEST_ERR) {
                    callback.onFailure(
                        App.instance.context().getString(R.string.check_email_password_again)
                    )
                }
            }

            override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
                Log.e("tag", t.toString())
            }
        })
    }

    override fun updateMember(
        memberNumber: Int,
        password: String?,
        nickName: String,
        phone: String,
        callback: MemberCallback<Boolean>
    ) {
        val requestBody = JsonObject().apply {
            addProperty(MEMBER_NUMBER, memberNumber)
            addProperty(PASSWORD, password)
            addProperty(NICKNAME, nickName)
            addProperty(PHONE, phone)
        }
        memberService.updateMember(memberNumber, requestBody)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.code() == SUCCESS) {
                        callback.onSuccess(true)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("tag", t.toString())
                }
            })

    }

    override fun deleteMember(memberNumber: Int, callback: MemberCallback<String>) {
        memberService.deleteMember(memberNumber).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == SUCCESS) {
                    callback.onSuccess(
                        App.instance.context().getString(R.string.success_delete_member)
                    )
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("tag", t.toString())
            }
        })
    }

    override fun checkEmail(email: String, callback: MemberCallback<EmailResponse>) {
        memberService.checkEmail(email).enqueue(object : Callback<EmailResponse> {
            override fun onFailure(call: Call<EmailResponse>, t: Throwable) {
                Log.e("tag", t.toString())
            }

            override fun onResponse(call: Call<EmailResponse>, response: Response<EmailResponse>) {
                if (response.code() == SUCCESS) {
                    response.body()?.let { callback.onSuccess(it) }
                }
            }

        })
    }

    override fun checkNickname(nickname: String, callback: MemberCallback<NicknameResponse>) {
        memberService.checkNickname(nickname).enqueue(object : Callback<NicknameResponse> {
            override fun onFailure(call: Call<NicknameResponse>, t: Throwable) {
                Log.e("tag", t.toString())
            }

            override fun onResponse(
                call: Call<NicknameResponse>,
                response: Response<NicknameResponse>
            ) {
                if (response.code() == SUCCESS) {
                    response.body()?.let { callback.onSuccess(it) }
                }
            }

        })
    }

    override fun findEmail(
        name: String,
        phone: String,
        callback: MemberCallback<List<EmailResponse>>
    ) {
        val requestBody = JsonObject().apply {
            addProperty(NAME, name)
            addProperty(PHONE, phone)
        }
        memberService.findEmail(requestBody)
            .enqueue(object : Callback<List<EmailResponse>> {
                override fun onFailure(call: Call<List<EmailResponse>>, t: Throwable) {
                    Log.e("tag", t.toString())
                }

                override fun onResponse(
                    call: Call<List<EmailResponse>>,
                    response: Response<List<EmailResponse>>
                ) {
                    if (response.code() == SUCCESS) {
                        response.body()?.let { callback.onSuccess(it) }
                    } else if (response.code() == REQUEST_ERR) {
                        callback.onFailure(
                            App.instance.context().getString(R.string.information_not_exist)
                        )
                    }
                }

            })
    }

    override fun findPassword(
        email: String,
        phone: String,
        callback: MemberCallback<FindPasswordResponse>
    ) {
        val requestBody = JsonObject().apply {
            addProperty(EMAIL, email)
            addProperty(PHONE, phone)
        }
        memberService.findPassword(requestBody)
            .enqueue(object : Callback<FindPasswordResponse> {
                override fun onFailure(call: Call<FindPasswordResponse>, t: Throwable) {
                    Log.e("tag", t.toString())
                }

                override fun onResponse(
                    call: Call<FindPasswordResponse>,
                    response: Response<FindPasswordResponse>
                ) {
                    if (response.code() == SUCCESS) {
                        response.body()?.let { callback.onSuccess(it) }
                    } else if (response.code() == REQUEST_ERR) {
                        callback.onFailure(
                            App.instance.context().getString(R.string.information_not_exist)
                        )
                    }
                }

            })
    }

    override fun checkSecurityCode(
        securityCode: String,
        email: String,
        phone: String,
        callback: MemberCallback<SecurityCodeResponse>
    ) {
        val requestBody = JsonObject().apply {
            addProperty(SECURITY_CODE, securityCode)
            addProperty(EMAIL, email)
            addProperty(Companion.PHONE, phone)
        }
        memberService.checkSecurityCode(requestBody)
            .enqueue(object : Callback<SecurityCodeResponse> {
                override fun onFailure(call: Call<SecurityCodeResponse>, t: Throwable) {
                    Log.e("tag", t.toString())
                }

                override fun onResponse(
                    call: Call<SecurityCodeResponse>,
                    response: Response<SecurityCodeResponse>
                ) {
                    if (response.code() == SUCCESS) {
                        response.body()?.let { callback.onSuccess(it) }
                    } else if (response.code() == REQUEST_ERR) {
                        callback.onFailure(
                            App.instance.context().getString(R.string.not_match_security_code)
                        )
                    }
                }

            })
    }

    override fun changePassword(
        password: String,
        memberNumber: Int,
        callback: MemberCallback<Boolean>
    ) {
        val jsonObject = JsonObject().apply {
            addProperty(PASSWORD, password)
            addProperty(MEMBER_NUMBER, memberNumber)
        }
        memberService.changePassword(jsonObject).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("tag", t.toString())
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == SUCCESS) {
                    callback.onSuccess(true)
                }
            }

        })
    }

    companion object {
        private const val MEMBER_NUMBER = "memberNumber"
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
        private const val NAME = "name"
        private const val NICKNAME = "nickName"
        private const val PHONE = "phoneNumber"
        private const val SECURITY_CODE = "securityCode"
        fun getInstance(memberApi: MemberApi): MemberRemoteDataSource =
            MemberRemoteDataSourceImpl(
                memberApi
            )
    }
}