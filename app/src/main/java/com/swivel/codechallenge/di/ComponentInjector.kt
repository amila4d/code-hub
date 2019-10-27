package com.swivel.codechallenge.di

import com.swivel.codechallenge.util.BASE_URL

class ComponentInjector {

    companion object {
        lateinit var component: AppComponent
        fun init() {
            component = DaggerAppComponent.builder()
                .apiModule(ApiModule(BASE_URL))
                .headlineNewsRepositoryModule(HeadlineNewsRepositoryModule())
                .build()
        }
    }
}