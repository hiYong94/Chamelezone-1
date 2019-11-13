package com.yeonae.chamelezone

import android.os.Bundle
import android.os.Handler
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_search_address.*

class SearchAddressActivity : AppCompatActivity() {
    private var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_address)
        setWebView()
        handler = Handler()
    }

    fun setWebView() {
        web_view!!.settings.javaScriptEnabled = true
        web_view!!.settings.javaScriptCanOpenWindowsAutomatically = true
        web_view!!.addJavascriptInterface(AndroidBridge(), "TestApp")
        web_view!!.webChromeClient = WebChromeClient()
        web_view!!.loadUrl("http://192.168.25.60:80/daum_address.php")
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
