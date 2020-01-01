package com.yeonae.chamelezone.view.login.presenter

interface JoinContract {

    interface View {
        var presenter: Presenter
        fun join()
    }

    interface Presenter {
        fun userRegister(email:String, password:String, name:String, nickname:String, phone:String)
    }
}