package com.yeonae.chamelezone.view.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.JsonObject
import com.yeonae.chamelezone.AlertDialogFragment
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.network.api.RetrofitConnection
import com.yeonae.chamelezone.network.model.MemberResponse
import com.yeonae.chamelezone.network.room.database.UserDatabase
import com.yeonae.chamelezone.network.room.entity.User
import kotlinx.android.synthetic.main.fragment_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {
    private val retrofitConnection = RetrofitConnection
    private val userDatabase by lazy {
        UserDatabase.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_back.setOnClickListener {
            requireActivity().finish()
        }
        btn_find_email.setOnClickListener {
            (activity as? LoginActivity)?.replace(FindEmailFragment(), true)
        }
        btn_find_password.setOnClickListener {
            (activity as LoginActivity).replace(FindPasswordFragment(), true)
        }
        btn_join.setOnClickListener {
            (activity as LoginActivity).replace(JoinFragment(), true)
        }
        btn_login.setOnClickListener {
            loginCheck("${edt_email.text}", "${edt_password.text}")
        }

    }

    private fun loginCheck(email: String, password: String) {
        when {
            email.isEmpty() -> Toast.makeText(
                requireContext(),
                "아이디를 입력해주세요!",
                Toast.LENGTH_SHORT
            ).show()
            password.isEmpty() -> Toast.makeText(
                requireContext(),
                "비밀번호를 입력해주세요!",
                Toast.LENGTH_SHORT
            ).show()
            else -> {
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
                    }

                    override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
                        Log.e("tag", t.toString())
                        showDialog()
                    }
                })
            }
        }
    }

    private fun showDialog() {
        val newFragment = AlertDialogFragment.newInstance(
            "입력하신 정보는 존재하지 않습니다."
        )
        newFragment.show(fragmentManager!!, "dialog")
    }

}