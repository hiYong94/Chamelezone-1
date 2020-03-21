package com.yeonae.chamelezone.ext

import android.content.Context
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.util.CustomRoundedCornersTransformation

fun View.catchFocus() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    isFocusableInTouchMode = true
    imm?.hideSoftInputFromWindow(windowToken, 0)
    requestFocus()
}

fun ImageView.glideImageSet(image: Int, width: Int, height: Int) {
    Glide.with(context)
        .load(image)
        .error(R.drawable.ic_x)
        .override(width, height)
        .centerCrop()
        .into(this)
}

fun ImageView.glideImageSet(image: String, width: Int, height: Int) {
    Glide.with(context)
        .load(image)
        .error(R.drawable.ic_x)
        .override(width, height)
        .centerCrop()
        .into(this)
}

fun ImageView.glideImageSet(image: Uri, width: Int, height: Int) {
    Glide.with(context)
        .load(image)
        .error(R.drawable.ic_x)
        .override(width, height)
        .centerCrop()
        .into(this)
}

fun ImageView.glideOriginImageSet(image: String, width: Int, height: Int) {
    Glide.with(context)
        .load(image)
        .error(R.drawable.ic_x)
        .override(width, height)
        .fitCenter()
        .into(this)
}

fun ImageView.glideTransformations(image: String, width: Int, height: Int) {
    val density = resources.displayMetrics.density

    Glide.with(context)
        .load(image)
        .error(R.drawable.ic_x)
        .override(width, height)
        .centerCrop()
        .transform(
            MultiTransformation(
                CenterCrop(),
                CustomRoundedCornersTransformation(
                    context,
                    (12 * density).toInt(),
                    0,
                    CustomRoundedCornersTransformation.CornerType.ALL
                )
            )
        )
        .placeholder(ContextCompat.getDrawable(context, R.drawable.home))
        .into(this)
}

fun Context.shortToast(resourceId: Int) {
    Toast.makeText(this, resourceId, Toast.LENGTH_SHORT)
        .show()
}

fun Context.shortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT)
        .show()
}

fun TextView.nextLineOptimize(){
    this.text = text.toString().replace(" ", "\u00A0")
}


