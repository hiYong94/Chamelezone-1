package com.yeonae.chamelezone.data.source.remote.member

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.JsonObject
import com.yeonae.chamelezone.App
import com.yeonae.chamelezone.network.api.MemberApi
import com.yeonae.chamelezone.network.api.RetrofitConnection
import com.yeonae.chamelezone.network.model.MemberResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MemberRemoteDataSourceImpl private constructor() :
    MemberRemoteDataSource {
    private val retrofitConnection = RetrofitConnection
    private val context: Context = App.instance.context()
    override fun createMember(
        email: String,
        passwrd: String,
        name: String,
        nickname: String,
        phone: String
    ) {
        val memberResponse = MemberResponse(
            1,
            email,
            passwrd,
            name,
            nickname,
            phone,
            ""
        )
        val jsonObject = JsonObject().apply {
            addProperty("email", memberResponse.email)
            addProperty("password", memberResponse.password)
            addProperty("name", memberResponse.name)
            addProperty("nickName", memberResponse.nickName)
            addProperty("phoneNumber", memberResponse.phoneNumber)
        }

        retrofitConnection.memberService.userRegister(jsonObject).enqueue(object :
            Callback<MemberResponse> {
            override fun onResponse(
                call: Call<MemberResponse>,
                response: Response<MemberResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "회원가입 성공", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
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
        fun getInstance(): MemberRemoteDataSource = MemberRemoteDataSourceImpl()
    }
}