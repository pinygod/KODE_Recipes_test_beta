package com.example.kode_recipes_test.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter(value = ["setImage"])
fun ImageView.setImageUrl(url: String?) {
    if (url != null && url.isNotBlank()) {
        Picasso.get()
            .load(url)
            .centerCrop()
            .fit()
            .into(this)
    }
}