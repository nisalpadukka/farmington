package com.georgian.farmington

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class MarketPlaceRecyclerViewAdapter(private val articleList: ArrayList<Article>, private val onArticleListner: OnArticleListner) : RecyclerView.Adapter<MarketPlaceRecyclerViewAdapter.ViewHolder>() {

    val storageRef = Firebase.storage.reference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_marketplace_card_layout, parent, false)
        return ViewHolder(v, onArticleListner)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*
        val article:Article = articleList[position]
        holder.articleTitle.text = article.title
        holder.articleSummary.text = article.summary
        holder.articlePos = position
        holder.articleImage.setImageResource(R.drawable.loading)
        val pathReference = storageRef.child("article_images/" + article.image)
        val localFile = File.createTempFile(article.image, "png")
        pathReference.getFile(localFile).addOnSuccessListener {
            holder.articleImage.setImageBitmap(BitmapFactory.decodeFile(localFile.absolutePath))
        }.addOnFailureListener{
            holder.articleImage.setImageResource(R.drawable.ricepic)
        }*/
    }

    override fun getItemCount(): Int {
        return articleList.size
    }
    inner class ViewHolder(articleView: View, onArticleListner: OnArticleListner): RecyclerView.ViewHolder(articleView){

        /*
        var articleImage:ImageView
        var articleTitle:TextView
        var articleSummary:TextView
        var articlePos:Int = 0;*/

        init {
            //articleImage = articleView.findViewById(R.id.article_image)
            //articleTitle = articleView.findViewById(R.id.article_title)
            //articleSummary = articleView.findViewById(R.id.article_summary)

            /*
            articleView.setOnClickListener(){
                onArticleListner.onArticleClick(articleList[articlePos])
            }*/

        }

    }

    public interface OnArticleListner{
        fun onArticleClick(article: Article){
        }
    }
}