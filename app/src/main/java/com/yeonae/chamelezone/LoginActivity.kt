package com.yeonae.chamelezone

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class LoginActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginFragment = LoginFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.login_fragment, loginFragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }
    fun replace(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.login_fragment, fragment)
        transaction.commit()
    }
}