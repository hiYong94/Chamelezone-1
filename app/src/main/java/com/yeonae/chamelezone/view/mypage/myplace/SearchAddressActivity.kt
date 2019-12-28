package com.yeonae.chamelezone.view.mypage.myplace

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.R
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
        web_view.run {
            webChromeClient = WebChromeClient()
            settings.setSupportMultipleWindows(true)
            settings.javaScriptEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            addJavascriptInterface(AndroidBridge(), "TestApp")
            web_view.webChromeClient = object : WebChromeClient() {
                @SuppressLint("SetJavaScriptEnabled")
                override fun onCreateWindow(
                    view: WebView?,
                    isDialog: Boolean,
                    isUserGesture: Boolean,
                    resultMsg: Message?
                ): Boolean {
                    val newWebView = WebView(this@SearchAddressActivity)
                    newWebView.settings.javaScriptEnabled = true
                    val dialog = Dialog(this@SearchAddressActivity)
                    dialog.setContentView(newWebView)
                    dialog.show()
                    dialog.window?.setLayout(1400, 2500)
                    newWebView.webChromeClient = object : WebChromeClient() {
                        override fun onCloseWindow(window: WebView) {
                            dialog.dismiss()
                        }
                    }
                    (resultMsg?.obj as? WebView.WebViewTransport)?.webView = newWebView
                    resultMsg?.sendToTarget()
                    return true
                }
            }
            loadUrl("http://13.209.136.122:3000/addressSearch")
        }
    }

    private inner class AndroidBridge {
        @JavascriptInterface
        fun setAddress(arg1: String, arg2: String, arg3: String) {
            handler?.post(Runnable {
                val address = String.format("%s %s", arg2, arg3)
                setWebView()
                val intent = Intent()
                intent.putExtra("result", address)
                setResult(RESULT_OK, intent)
                finish()
            })
        }
    }
}
