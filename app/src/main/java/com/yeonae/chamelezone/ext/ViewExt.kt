package com.yeonae.chamelezone.ext

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide

fun View.catchFocus(context: Context){
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    isFocusableInTouchMode = true
    imm?.hideSoftInputFromWindow(windowToken, 0)
    requestFocus()
}

fun ImageView.glideImageSet(image: Int, width: Int, height: Int) {
    Glide.with(context)
        .load(image)
        .override(width, height)
        .centerCrop()
        .into(this)
}

fun Activity.glideImageUriSet(image: Uri, width: Int, height: Int, imageView: ImageView) {
    Glide.with(this)
        .load(image)
        .override(width, height)
        .centerCrop()
        .into(imageView)
}