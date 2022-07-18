package com.georgian.farmington

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class MarketPlaceRecyclerViewAdapter(private val productList: ArrayList<Product>, private val onProductListner: OnProductListner) : RecyclerView.Adapter<MarketPlaceRecyclerViewAdapter.ViewHolder>() {

    val storageRef = Firebase.storage.reference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_marketplace_card_layout, parent, false)
        return ViewHolder(v, onProductListner)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.productPos = position

        val product:Product = productList[position]
        holder.producNameLeft.text = product.product_name
        holder.productImageLeft.setImageResource(R.drawable.loading)
        val pathReference = storageRef.child("product_images/" + product.image)
        val localFile = File.createTempFile(product.image, "png")
        pathReference.getFile(localFile).addOnSuccessListener {
            holder.productImageLeft.setImageBitmap(BitmapFactory.decodeFile(localFile.absolutePath))
        }.addOnFailureListener{
            holder.productImageLeft.setImageResource(R.drawable.ricepic)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
    inner class ViewHolder(productView: View, onProductListner: OnProductListner): RecyclerView.ViewHolder(productView){

        var productImageLeft:ImageView
        var producNameLeft:TextView
        var productPos:Int = 0
        var productCardLayoutLeft:CardView


        init {
            productImageLeft = productView.findViewById(R.id.leftItemImage)
            producNameLeft = productView.findViewById(R.id.leftItemProductName)
            productCardLayoutLeft = productView.findViewById(R.id.market_place_card_view)


            productCardLayoutLeft.setOnClickListener(){
                onProductListner.onProductListner(productList[productPos])
            }

            productImageLeft.setOnClickListener(){
                onProductListner.onProductListner(productList[productPos])
            }

        }

    }

    interface OnProductListner{
        fun onProductListner(product: Product){
        }
    }
}