package com.example.rickandmorty.utils.common.extensions

import android.widget.TextView

fun TextView.setCustomText(value: Int): String {
    return context.getString(value)
}

fun TextView.setCustomText(value: Int, additionalValue: String){
    setText(context.getString(value) + " " + additionalValue)
}

fun TextView.getCustomText(value: Int, additionalValue: String): String{
    return context.getString(value) + " " + additionalValue
}