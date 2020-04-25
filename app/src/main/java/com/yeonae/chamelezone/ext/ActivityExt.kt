package com.yeonae.chamelezone.ext

import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.view.LoadingDialogFragment

fun AppCompatActivity.showLoading() {
    LoadingDialogFragment().show(supportFragmentManager, "dialog")
}

fun AppCompatActivity.hideLoading() {
    supportFragmentManager.findFragmentByTag("dialog")?.let {
        (it as? LoadingDialogFragment)?.dismiss()
    }
}