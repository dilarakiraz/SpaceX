package com.dilara.spacex.util

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun ImageView.downloadImage(url:String){
    Glide.with(context).load(url).into(this)
}
fun ImageView.downloadImageForCarousel(url:String){
    Glide.with(context)
        .load(Uri.parse(url))
        .transition(DrawableTransitionOptions.withCrossFade())
        .transform(CenterCrop())
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(this)
}