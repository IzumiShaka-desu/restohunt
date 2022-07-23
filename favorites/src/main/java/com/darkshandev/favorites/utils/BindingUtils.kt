package com.darkshandev.restohunt.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["resname", "isExpand"], requireAll = false)
fun loadImageDrawable(view: ImageView, name: String?, isExpand: Boolean?) {
    name?.let {
        val isExpanded = isExpand ?: false
        view.loadImage(
            if (isExpanded) "https://restaurant-api.dicoding.dev/images/large/$it" else "https://restaurant-api.dicoding.dev/images/medium/$it",
            isExpand = isExpanded
        )
    }
}