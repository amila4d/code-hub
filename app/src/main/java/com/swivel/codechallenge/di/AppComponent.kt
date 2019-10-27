package com.swivel.codechallenge.di

import com.swivel.codechallenge.ui.customnews.CustomNewsViewModel
import com.swivel.codechallenge.ui.headlinenews.HeadlineNewsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        ApiModule::class,
        HeadlineNewsRepositoryModule::class,
        CustomNewsRepositoryModule::class
    )
)
interface AppComponent {
    fun inject(headlineNewsViewModel: HeadlineNewsViewModel)
    fun injectCustomNews(customNewsViewModel: CustomNewsViewModel)
}