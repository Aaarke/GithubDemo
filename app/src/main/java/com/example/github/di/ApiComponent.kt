package com.example.github.di

import com.example.github.home.HomeViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])


interface ApiComponent {
    @Component.Builder
    interface Builder {
        fun build(): ApiComponent
        fun networkModule(networkModule: NetworkModule): Builder
    }

    fun inject(homeViewModel: HomeViewModel)


}