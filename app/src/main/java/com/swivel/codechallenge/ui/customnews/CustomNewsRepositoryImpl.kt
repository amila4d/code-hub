package com.swivel.codechallenge.ui.customnews

import com.swivel.codechallenge.model.NewsResponse
import com.swivel.codechallenge.network.NetworkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomNewsRepositoryImpl(private val networkService: NetworkService) : CustomNewsRepository{

    override fun getCustomNews(networkUrl: String, successHandler: (NewsResponse?) -> Unit, failureHandler: (Throwable?) -> Unit) {
        networkService.getNewsAsync(networkUrl).enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>?, response: Response<NewsResponse>?) {
                response?.body()?.let {
                    successHandler(it)
                }
            }

            override fun onFailure(call: Call<NewsResponse>?, t: Throwable?) {
                failureHandler(t)
            }
        })
    }
}