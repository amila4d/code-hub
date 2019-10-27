package com.swivel.codechallenge.ui.detailnews

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.swivel.codechallenge.R
import com.swivel.codechallenge.model.Article

class DetailNewsActivity : AppCompatActivity() {

    internal lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)
        context = this

        val articleObj = intent?.extras!!.getSerializable(getString(R.string.article)) as? Article

        val tvNewsDetail  = findViewById<TextView>(R.id.tvNewsDetail)
        val tvUrl  = findViewById<TextView>(R.id.tvUrl)
        val ivNews  = findViewById<ImageView>(R.id.ivNewsImage)

        tvNewsDetail.text = articleObj!!.description
        Glide.with(this).load(articleObj!!.urlToImage)
            .apply(RequestOptions().centerCrop())
            .into(ivNews)
        tvUrl.text = articleObj!!.url
        title = articleObj!!.title
    }
}