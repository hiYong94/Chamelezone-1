package com.yeonae.chamelezone.ext

import android.content.Context
import android.net.Uri
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
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

fun ImageView.glideTransformations(image: String, width: Int, height: Int) {
    val outMetrics = DisplayMetrics()
    val density = outMetrics.densityDpi

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
                    25 * density,
                    0,
                    CustomRoundedCornersTransformation.CornerType.ALL
                )
            )
        )
        .placeholder(ContextCompat.getDrawable(context, R.drawable.home))
        .into(this)
}