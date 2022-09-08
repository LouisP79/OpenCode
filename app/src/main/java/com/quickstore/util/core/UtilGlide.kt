package com.quickstore.util.core

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.quickstore.R

fun glide(context: Context, imageView: ImageView, path: String) {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent))
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    Glide.with(context)
        .load(path)
        .placeholder(circularProgressDrawable)
        .error(R.drawable.img_empty_image)
        .into(imageView)
}