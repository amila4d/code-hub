package com.swivel.codechallenge.di

import com.google.gson.GsonBuilder
import com.swivel.codechallenge.network.NetworkService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.swivel.codechallenge.network.UnsafeOkHttpClient


@Module
class ApiModule(private val baseUrl: String) {
    @Provides
    @Singleton
    fun provideApiService(): NetworkService {
        val gson = GsonBuilder().create()
        val okHttpClient = UnsafeOkHttpClient.unsafeOkHttpClient

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(NetworkService::class.java)
    }
}