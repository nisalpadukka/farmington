package com.georgian.farmington

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AgriNewsRecyclerViewAdapter(private val articleList: ArrayList<Article>) : RecyclerView.Adapter<AgriNewsRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.agri_news_home_card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article:Article = articleList[position]
        holder.articleTitle.text = article.title
        holder.articleDesc.text = article.description
        //holder.articleImage.setImageResource(articleList[position])
    }

    override fun getItemCount(): Int {
        return articleList.size
    }
    inner class ViewHolder(articleView: View): RecyclerView.ViewHolder(articleView){

        var articleImage:ImageView
        var articleTitle:TextView
        var articleDesc:TextView

        init {
            articleImage = articleView.findViewById(R.id.article_image)
            articleTitle = articleView.findViewById(R.id.article_title)
            articleDesc = articleView.findViewById(R.id.article_detail)
        }
    }
}