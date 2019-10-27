package com.swivel.codechallenge.ui.headlinenews

import com.swivel.codechallenge.model.NewsResponse

interface HeadlineNewsRepository {
    fun getHeadlineNews(networkUrl: String, successHandler: (NewsResponse?) -> Unit, failureHandler: (Throwable?) -> Unit)
}