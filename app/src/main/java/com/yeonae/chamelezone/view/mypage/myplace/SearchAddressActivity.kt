package com.yeonae.chamelezone.view.mypage.myplace

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.R
import kotlinx.android.synthetic.main.activity_search_address.*
import android.webkit.WebViewClient

class SearchAddressActivity : AppCompatActivity() {
    private var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_address)
        setWebView()
        handler = Handler()
    }

    fun setWebView() {
        web_view.run {
            webChromeClient = WebChromeClient() // 웹뷰에 크롬 사용 허용
            settings.setSupportMultipleWindows(true)
            settings.javaScriptEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.useWideViewPort = true // 화면 사이즈 맞추기 허용 여부
            settings.setSupportZoom(true) // 화면 줌 허용 여부
            addJavascriptInterface(AndroidBridge(), "TestApp")
            web_view.webChromeClient = object : WebChromeClient() {
                override fun onCreateWindow(
                    view: WebView?,
                    isDialog: Boolean,
                    isUserGesture: Boolean,
                    resultMsg: Message?
                ): Boolean {

                    return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
                }
            }
            loadUrl("http://13.209.136.122:3000/addressSearch")
        }
    }

    private inner class AndroidBridge {
        @JavascriptInterface
        fun setAddress(arg1: String, arg2: String, arg3: String) {
            handler?.post(Runnable {
                tv_address.text = String.format("(%s) %s %s", arg1, arg2, arg3)
                setWebView()
            })
        }
    }
}
