package com.yeonae.chamelezone.data.source.remote.member

import android.util.Log
import com.google.gson.JsonObject
import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.network.api.RetrofitConnection
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MemberRemoteDataSourceImpl private constructor(private var retrofitConnection: RetrofitConnection) :
    MemberRemoteDataSource {
    override fun createMember(
        email: String,
        password: String,
        name: String,
        nickName: String,
        phone: String,
        callBack: MemberCallBack
    ) {
        val jsonObject = JsonObject().apply {
            addProperty("email", email)
            addProperty("password", password)
            addProperty("name", name)
            addProperty("nickName", nickName)
            addProperty("phoneNumber", phone)
        }

        retrofitConnection.memberService.userRegister(jsonObject).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                callBack.onSuccess("회원가입 성공")
                Log.d("tag", response.body().toString())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
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
        fun getInstance(retrofitConnection: RetrofitConnection): MemberRemoteDataSource =
            MemberRemoteDataSourceImpl(retrofitConnection)
    }
}