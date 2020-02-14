package com.yeonae.chamelezone.data.source.remote.member

import android.util.Log
import com.google.gson.JsonObject
import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.network.api.MemberApi
import com.yeonae.chamelezone.network.api.RetrofitConnection.memberService
import com.yeonae.chamelezone.network.model.MemberResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MemberRemoteDataSourceImpl private constructor(private val memberApi: MemberApi) :
    MemberRemoteDataSource {
    override fun createMember(
        email: String,
        password: String,
        name: String,
        nickName: String,
        phone: String,
        callBack: MemberCallBack<String>
    ) {
        val jsonObject = JsonObject().apply {
            addProperty("email", email)
            addProperty("password", password)
            addProperty("name", name)
            addProperty("nickName", nickName)
            addProperty("phoneNumber", phone)
        }

        memberService.createMember(jsonObject).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.code() == SUCCESS) {
                    callBack.onSuccess("회원가입 성공")
                }
                Log.d("err", response.code().toString())
                Log.d("tag", response.body().toString())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("tag", t.toString())
            }
        })

    }

    override fun login(
        email: String,
        password: String,
        callBack: MemberCallBack<MemberResponse>
    ) {
        val jsonObject = JsonObject().apply {
            addProperty("email", email)
            addProperty("password", password)
        }

        memberService.getMember(jsonObject).enqueue(object :
            Callback<MemberResponse> {
            override fun onResponse(
                call: Call<MemberResponse>,
                response: Response<MemberResponse>
            ) {
                if (response.code() == SUCCESS) {
                    Log.d("MyCall", response.body().toString())
                    response.body()?.let { callBack.onSuccess(it) }
                } else if (response.code() == REQUEST_ERR) {
                    callBack.onFailure("이메일과 비밀번호를 확인 후 다시 로그인해주세요.")
                }
            }

            override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
                Log.e("tag", t.toString())
            }
        })
    }

    override fun updateMember(
        memberNumber: Int,
        password: String,
        nickName: String,
        phone: String,
        callBack: MemberCallBack<Boolean>
    ) {
        val jsonObject = JsonObject().apply {
            addProperty("memberNumber", memberNumber)
            addProperty("password", password)
            addProperty("nickName", nickName)
            addProperty("phoneNumber", phone)
        }
        memberService.updateMember(memberNumber, jsonObject)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.code() == SUCCESS) {
                        callBack.onSuccess(true)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("tag", t.toString())
                }
            })

    }

    override fun deleteMember(memberNumber: Int, callBack: MemberCallBack<String>) {
        memberService.deleteMember(memberNumber).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == SUCCESS) {
                    callBack.onSuccess("회원 탈퇴 성공")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }
        })
    }

    companion object {
        private const val SUCCESS = 200
        private const val REQUEST_ERR = 404
        fun getInstance(memberApi: MemberApi): MemberRemoteDataSource =
            MemberRemoteDataSourceImpl(memberApi)
    }
}