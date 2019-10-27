package com.swivel.codechallenge.di

import com.swivel.codechallenge.network.NetworkService
import com.swivel.codechallenge.ui.customnews.CustomNewsRepository
import com.swivel.codechallenge.ui.customnews.CustomNewsRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CustomNewsRepositoryModule {
    @Provides
    @Singleton
    fun providePostRepository(apiService: NetworkService): CustomNewsRepository = CustomNewsRepositoryImpl(apiService)
}