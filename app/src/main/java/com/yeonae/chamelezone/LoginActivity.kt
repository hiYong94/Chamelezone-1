package com.yeonae.chamelezone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginFragment = LoginFragment()
        replace(loginFragment)

    }

    fun replace(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.login_fragment, fragment).addToBackStack(null).commit()
    }

    fun back(fragment: Fragment){
        supportFragmentManager.beginTransaction().remove(fragment).commit()
        supportFragmentManager.popBackStack()
    }

}