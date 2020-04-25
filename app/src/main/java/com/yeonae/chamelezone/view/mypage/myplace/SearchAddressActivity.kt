package com.yeonae.chamelezone.view.mypage.myplace

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
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

    @SuppressLint("SetJavaScriptEnabled")
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
                    dialog.setOnCancelListener { finish() }
                    dialog.window?.setLayout(web_view.width, web_view.height)
                    Log.d("tag", web_view.width.toString())
                    Log.d("tag", web_view.height.toString())
                    newWebView.webChromeClient = object : WebChromeClient() {
                        override fun onCloseWindow(window: WebView) {
                            dialog.dismiss()
                            newWebView.destroy()
                        }
                    }
                    (resultMsg?.obj as? WebView.WebViewTransport)?.webView = newWebView
                    resultMsg?.sendToTarget()
                    return true
                }
            }
            loadUrl("https://shopinshop.tk/addressSearch")
        }
    }

    private inner class AndroidBridge {
        @JavascriptInterface
        fun setAddress(arg1: String, arg2: String, arg3: String) {
            handler?.post {
                val address = String.format("%s %s", arg2, arg3)
                setWebView()
                val intent = Intent()
                intent.putExtra("result", address)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
}

