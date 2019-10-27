package com.swivel.codechallenge.ui.headlinenews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swivel.codechallenge.model.Article
import com.swivel.codechallenge.model.NewsResponse
import com.swivel.codechallenge.util.API_KEY
import javax.inject.Inject

class HeadlineNewsViewModel : ViewModel() {

    @Inject
    lateinit var headlineNewsRepository: HeadlineNewsRepository
    var isLoading = MutableLiveData<Boolean>()
    var apiError = MutableLiveData<Throwable>()
    var newsResponse = MutableLiveData<NewsResponse>()

    fun getHeadlineNews() {
        isLoading.value = true
        headlineNewsRepository.getHeadlineNews(
            "top-headlines?country=us&category=business&apiKey=$API_KEY",
            {
                newsResponse.value = it
                isLoading.value = false
            },

            {
                apiError.value = it
                isLoading.value = false
            })
    }

    /**
     * Adapter Callback
     */

    fun getHeadlineNewsAt(position: Int): Article? {
        if (position < getHeadlineNewsSize()) {
            return newsResponse.value?.articles?.get(position)
        } else {
            return null
        }
    }

    fun getHeadlineNewsSize(): Int {
        newsResponse.value?.articles?.let {
            return it.size
        }
        return 0
    }

}