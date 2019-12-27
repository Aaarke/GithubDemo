package com.example.github.base

import androidx.lifecycle.ViewModel
import com.example.github.di.ApiComponent
import com.example.github.di.DaggerApiComponent
import com.example.github.di.NetworkModule
import com.example.github.home.HomeViewModel


abstract class BaseViewModel : ViewModel() {


    private val injector: ApiComponent = DaggerApiComponent
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is HomeViewModel -> {
                injector.inject(this)
            }
        }
    }
}