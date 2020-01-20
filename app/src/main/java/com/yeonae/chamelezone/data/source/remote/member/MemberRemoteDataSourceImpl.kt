package com.yeonae.chamelezone.data.source.remote.member

import android.util.Log
import com.google.gson.JsonObject
import com.yeonae.chamelezone.App
import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.network.api.MemberApi
import com.yeonae.chamelezone.network.api.RetrofitConnection.memberService
import com.yeonae.chamelezone.network.model.MemberResponse
import com.yeonae.chamelezone.network.room.database.UserDatabase
import com.yeonae.chamelezone.network.room.entity.User
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

        memberService.userRegister(jsonObject).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.isSuccessful){
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

    override fun login(email: String, password: String, callBack: MemberCallBack<MemberResponse>) {
        val jsonObject = JsonObject().apply {
            addProperty("email", email)
            addProperty("password", password)
        }

        memberService.login(jsonObject).enqueue(object :
            Callback<List<MemberResponse>> {
            override fun onResponse(
                call: Call<List<MemberResponse>>,
                response: Response<List<MemberResponse>>
            ) {
                if (response.isSuccessful){
                    Log.d("MyCall", response.body().toString())
                    callBack.onSuccess(response.body()!![0])
                }
            }

            override fun onFailure(call: Call<List<MemberResponse>>, t: Throwable) {
                Log.e("tag", t.toString())
            }
        })
    }

    override fun getMember() {

    }

    override fun deleteMember() {

    }

    override fun updateMember() {

    }

    companion object {
        fun getInstance(memberApi: MemberApi): MemberRemoteDataSource =
            MemberRemoteDataSourceImpl(memberApi)
    }
}