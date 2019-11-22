package com.yeonae.chamelezone.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.yeonae.chamelezone.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginFragment = LoginFragment()
        replace(loginFragment, false)
    }

    fun replace(fragment: Fragment, isBackStack: Boolean = true) {
        if(isBackStack){
            supportFragmentManager.beginTransaction().replace(R.id.login_fragment, fragment).addToBackStack(null).commit()
        }else{
            supportFragmentManager.beginTransaction().replace(R.id.login_fragment, fragment).commit()

        }

    }

    fun back(fragment: Fragment){
        supportFragmentManager.beginTransaction().remove(fragment).commit()
        supportFragmentManager.popBackStack()
    }

}