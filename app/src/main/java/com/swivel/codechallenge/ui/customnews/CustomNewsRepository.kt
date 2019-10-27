package com.swivel.codechallenge.ui.customnews

import com.swivel.codechallenge.model.NewsResponse

interface CustomNewsRepository {
    fun getCustomNews(networkUrl: String,successHandler: (NewsResponse?) -> Unit, failureHandler: (Throwable?) -> Unit)
}