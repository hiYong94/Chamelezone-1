package com.yeonae.chamelezone.ext

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.catchFocus(context: Context){
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    isFocusableInTouchMode = true
    imm?.hideSoftInputFromWindow(windowToken, 0)
    requestFocus()
}