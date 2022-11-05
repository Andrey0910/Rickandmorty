package com.example.rickandmorty.utils.common.extensions

import android.view.View
import dev.chrisbanes.insetter.applyInsetter

fun View.insetterTop() {
    applyInsetter {
        ignoreVisibility(true)
        type(statusBars = true, displayCutout = true) {
            margin(animated = true)
        }
    }
}

fun View.insetterRecyclerBottom() {
    applyInsetter {
        ignoreVisibility(true)
        type(navigationBars = true) {
            padding(bottom = true)
        }
    }
}