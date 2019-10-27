package com.swivel.codechallenge.ui.customnews

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.card.MaterialCardView
import com.swivel.codechallenge.R
import com.swivel.codechallenge.model.Article
import com.swivel.codechallenge.ui.detailnews.DetailNewsActivity
import java.io.Serializable

class RecyclerAdapterCustomNews (private val context: Context, private var customNewsViewModel: CustomNewsViewModel) : RecyclerView.Adapter<RecyclerAdapterCustomNews.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_news_headline,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return customNewsViewModel.getCustomNewsSize()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        customNewsViewModel.getCustomNewsAt(position)?.let {
            holder.apply {
                val article = it
                tvNewsHeadline.text = it.title
                Glide.with(context).load(it.urlToImage)
                    .apply(RequestOptions().centerCrop())
                    .into(ivNewsImage)
                cardView.setOnClickListener {
                    val intent = Intent(context, DetailNewsActivity::class.java).apply {
                        putExtra(context.getString(R.string.article), article as Serializable)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val tvNewsHeadline by lazy { itemView!!.findViewById(R.id.tvNewsHeadline) as TextView }
        val ivNewsImage by lazy { itemView!!.findViewById(R.id.ivNewsImage) as ImageView }
        val cardView by lazy { itemView!!.findViewById(R.id.cardView) as MaterialCardView }
    }
}