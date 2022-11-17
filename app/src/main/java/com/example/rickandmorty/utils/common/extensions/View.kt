package com.example.rickandmorty.utils.common.extensions

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.rickandmorty.databinding.ItemSnackBarBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
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

fun View.showCustomSnackBar(text: String, imjRes: Int, res: Int, duration: Int = 3000): Snackbar {
    val snackBar = Snackbar.make(this, "", duration)
    val nullParent: ViewGroup? = null
    val layoutInflater = LayoutInflater.from(context)
    val binding: ItemSnackBarBinding = ItemSnackBarBinding.inflate(layoutInflater, nullParent, false)
    binding.tvNotInternet.text = text
    binding.imNotInternet.setImageDrawable(context.returnDrawable(imjRes))
    binding.clNotInternet.background = context.returnDrawable(res)
    snackBar.view.setBackgroundColor(Color.TRANSPARENT)
    val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout
    snackBarLayout.setPadding(0, 0, 0, 0)
    snackBarLayout.addView(binding.root, 0)
    snackBar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
    snackBar.show()
    return snackBar
}

fun Context.returnDrawable(id: Int) = ContextCompat.getDrawable(this, id)