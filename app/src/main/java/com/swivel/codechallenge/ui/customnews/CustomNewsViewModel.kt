package com.swivel.codechallenge.ui.customnews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swivel.codechallenge.model.Article
import com.swivel.codechallenge.model.NewsResponse
import javax.inject.Inject

class CustomNewsViewModel : ViewModel() {

    @Inject
    lateinit var customNewsRepository: CustomNewsRepository
    var isLoading = MutableLiveData<Boolean>()
    var apiError = MutableLiveData<Throwable>()
    var newsResponse = MutableLiveData<NewsResponse>()

    fun getCustomNews(networkUrl: String) {
        isLoading.value = true
        customNewsRepository.getCustomNews(networkUrl,
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

    fun getCustomNewsAt(position: Int): Article? {
        if (position < getCustomNewsSize()) {
            return newsResponse.value?.articles?.get(position)
        } else {
            return null
        }
    }

    fun getCustomNewsSize(): Int {
        newsResponse.value?.articles?.let {
            return it.size
        }
        return 0
    }
}