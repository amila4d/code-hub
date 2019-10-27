package com.swivel.codechallenge.network

import com.swivel.codechallenge.model.NewsResponse
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {
    //Get news
    @GET
    fun getNewsAsync(@Url url: String) : Call<NewsResponse>
}
