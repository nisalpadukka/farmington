package com.georgian.farmington

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AgriNewsRecyclerViewAdapter(private val articleList: ArrayList<Article>, private val onArticleListner: OnArticleListner) : RecyclerView.Adapter<AgriNewsRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.agri_news_home_card_layout, parent, false)
        return ViewHolder(v, onArticleListner)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article:Article = articleList[position]
        holder.articleTitle.text = article.title
        holder.articleSummary.text = article.summary;
        //holder.articleImage.setImageResource(articleList[position])
    }

    override fun getItemCount(): Int {
        return articleList.size
    }
    inner class ViewHolder(articleView: View, onArticleListner: OnArticleListner): RecyclerView.ViewHolder(articleView){

        var articleImage:ImageView
        var articleTitle:TextView
        var articleSummary:TextView

        init {
            articleImage = articleView.findViewById(R.id.article_image)
            articleTitle = articleView.findViewById(R.id.article_title)
            articleSummary = articleView.findViewById(R.id.article_summary)

            articleView.setOnClickListener(){
                onArticleListner.onArticleClick()
            }

        }

    }

    public interface OnArticleListner{
        fun onArticleClick(){
        }
    }
}