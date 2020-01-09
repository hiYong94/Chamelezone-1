package com.yeonae.chamelezone.data.source.remote.member

import android.util.Log
import com.google.gson.JsonObject
import com.yeonae.chamelezone.App
import com.yeonae.chamelezone.data.repository.member.MemberCallBack
import com.yeonae.chamelezone.network.api.RetrofitConnection
import com.yeonae.chamelezone.network.model.MemberResponse
import com.yeonae.chamelezone.network.room.database.UserDatabase
import com.yeonae.chamelezone.network.room.entity.User
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MemberRemoteDataSourceImpl private constructor(private var retrofitConnection: RetrofitConnection) :
    MemberRemoteDataSource {
    private val userDatabase by lazy {
        UserDatabase.getInstance(App.instance.context())
    }
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

    override fun login(email: String, password: String, callBack: MemberCallBack) {
        val jsonObject = JsonObject().apply {
            addProperty("email", email)
            addProperty("password", password)
        }

        retrofitConnection.memberService.login(jsonObject).enqueue(object :
            Callback<MemberResponse> {
            override fun onResponse(
                call: Call<MemberResponse>,
                response: Response<MemberResponse>
            ) {
                val r = Runnable {
                    val newUser = User(
                        response.body()!!.memberNumber,
                        response.body()!!.email,
                        response.body()!!.name,
                        response.body()!!.nickName,
                        response.body()!!.phoneNumber
                    )
                    userDatabase?.userDao()?.insertAll(newUser)
                    Log.d("user", userDatabase?.userDao()?.getAll().toString())
                }
                val thread = Thread(r)
                thread.start()
                callBack.onSuccess("로그인 성공")
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
        fun getInstance(retrofitConnection: RetrofitConnection): MemberRemoteDataSource =
            MemberRemoteDataSourceImpl(retrofitConnection)
    }
}