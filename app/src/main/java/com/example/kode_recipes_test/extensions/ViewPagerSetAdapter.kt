package com.example.kode_recipes_test.extensions

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.viewpager2.widget.ViewPager2

@BindingAdapter(value = ["setAdapter"])
fun ViewPager2.bindRecyclerViewAdapter(adapter: ListAdapter<*, *>) {
    this.run {
        this.adapter = adapter
        offscreenPageLimit = 4
    }
}