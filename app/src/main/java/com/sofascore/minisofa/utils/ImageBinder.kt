package com.sofascore.minisofa.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sofascore.minisofa.R

@BindingAdapter("imageUrl")
fun bindImage(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .apply(RequestOptions().placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_error))
            .into(view)
    }
}
