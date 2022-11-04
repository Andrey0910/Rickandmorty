package com.example.rickandmorty.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T: Any> Fragment.nullOnDestroy(): NullOnDestroy<T> = NullOnDestroy(this)

class NullOnDestroy<T: Any> (
    fragment: Fragment
) : ReadWriteProperty<Fragment, T>, DefaultLifecycleObserver{

    private var value: T? = null

    init {
        fragment.viewLifecycleOwnerLiveData.observeForever {
            it?.lifecycle?.addObserver(this)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        value = null
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("Property ${property.name} should be initialized before get.")
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        this.value = value
    }
}