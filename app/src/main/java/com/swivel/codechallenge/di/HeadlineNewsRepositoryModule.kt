package com.swivel.codechallenge.di

import com.swivel.codechallenge.network.NetworkService
import com.swivel.codechallenge.ui.headlinenews.HeadlineNewsRepository
import com.swivel.codechallenge.ui.headlinenews.HeadlineNewsRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class HeadlineNewsRepositoryModule {
    @Provides
    @Singleton
    fun providePostRepository(apiService: NetworkService): HeadlineNewsRepository = HeadlineNewsRepositoryImpl(apiService)
}