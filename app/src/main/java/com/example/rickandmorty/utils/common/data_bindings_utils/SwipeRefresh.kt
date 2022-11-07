package com.example.rickandmorty.utils.common.data_bindings_utils

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.rickandmorty.utils.common.extensions.px

class SwipeRefresh {

    object SwipeRefreshBindingAdapter {

        @BindingAdapter("backgroundColorScheme")
        @JvmStatic
        fun SwipeRefreshLayout.setBackgroundColorScheme(colorRes: Int) {
            this.setProgressBackgroundColorSchemeColor(colorRes)
        }

        @BindingAdapter("setColorSchemeColors")
        @JvmStatic
        fun SwipeRefreshLayout.setColorSchemeColors(colorRes: Int) {
            this.setColorSchemeColors(colorRes)
        }

        @BindingAdapter("setProgressViewOffset")
        @JvmStatic
        fun SwipeRefreshLayout.setProgressViewOffset(end: Float) {
            this.setProgressViewOffset(true, 0, end.px.toInt())
        }
    }

}