package com.georgian.farmington

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class ProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val product = intent.getParcelableExtra("product") as? Product
        this.supportActionBar?.title = "Farmington - " + product?.title
        findViewById<TextView>(R.id.textView3).text = product?.title + "              " + product?.price + " $/Kg"
        findViewById<TextView>(R.id.textView2).text = product?.description
        var img = R.drawable.barley;
        img = product!!?.imgUri;
        findViewById<ImageView>(R.id.imageView2).setImageResource(img);
    }
}