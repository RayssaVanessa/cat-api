package com.example.catapi.presentation.adapter.imageview

import android.widget.ImageView
import com.bumptech.glide.Glide

internal fun ImageView.loadImageFromUrl(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}