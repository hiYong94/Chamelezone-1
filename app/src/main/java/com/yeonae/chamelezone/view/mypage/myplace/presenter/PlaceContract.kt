package com.yeonae.chamelezone.view.mypage.myplace.presenter

interface PlaceContract {

    interface View {
        var presenter: Presenter
        fun place()
    }

    interface Presenter {
        fun placeRegister(keywordNumber:Int, name:String, address:String, openingTime:String, phoneNumber:String, content:String)
    }
}