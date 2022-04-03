package com.langga.movieapp.core.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.langga.movieapp.core.R

fun Context.loadImage(url:String, imageView: ImageView){
    Glide.with(this)
        .load(url)
        .centerCrop()
        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading_image)
            .error(R.drawable.ic_error_image))
        .into(imageView)
}

fun View.visible(){
    visibility = View.VISIBLE
}

