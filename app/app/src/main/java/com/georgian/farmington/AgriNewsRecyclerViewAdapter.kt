package com.georgian.farmington

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import java.io.IOException


class AgriNewsRecyclerViewAdapter : RecyclerView.Adapter<AgriNewsRecyclerViewAdapter.ViewHolder>() {

    private val client = OkHttpClient()

    private var titles = arrayOf("Agricultural News Heading 1", "Agricultural News Heading 2", "Agricultural News Heading 3",
        "Agricultural News Heading 4","Agricultural News Heading 5","Agricultural News Heading 6")
    private var details = arrayOf("Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry.")
    private var images = intArrayOf(R.drawable.farmer_news, R.drawable.farmer_news,
        R.drawable.farmer_news, R.drawable.farmer_news, R.drawable.farmer_news, R.drawable.farmer_news)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.agri_news_home_card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.articleTitle.text = titles[position]
        holder.articleDesc.text = details[position]
        holder.articleImage.setImageResource(images[position])
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    fun run(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i("API", e.toString())
            }
            override fun onResponse(call: Call, response: Response) {
                Log.i("API", response.body()?.string().toString())
            }
        })
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