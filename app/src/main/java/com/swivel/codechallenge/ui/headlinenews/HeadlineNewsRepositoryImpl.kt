package com.swivel.codechallenge.ui.headlinenews

import com.swivel.codechallenge.model.NewsResponse
import com.swivel.codechallenge.network.NetworkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HeadlineNewsRepositoryImpl(private val networkService: NetworkService) : HeadlineNewsRepository{

    override fun getHeadlineNews(networkUrl: String, successHandler: (NewsResponse?) -> Unit, failureHandler: (Throwable?) -> Unit) {
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