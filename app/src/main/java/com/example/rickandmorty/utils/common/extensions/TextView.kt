package com.example.rickandmorty.utils.common.extensions

import android.widget.TextView

fun TextView.setCustomText(value: Int){
    text = context.getString(value)
}

fun TextView.setCustomText(value: Int, additionalValue: String){
    setText(context.getString(value) + " " + additionalValue)
}