package com.swivel.codechallenge.model

import java.io.Serializable

data class NewsResponse (
    val status: String? = null,
    val totalResults: Int? = null,
    val articles: List<Article>? = null
): Serializable