package com.yeonae.chamelezone

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun context() = applicationContext

    companion object{
        lateinit var instance: App
            private set

    }
}