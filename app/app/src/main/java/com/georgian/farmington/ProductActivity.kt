package com.georgian.farmington

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.georgian.farmington.databinding.ActivityNewsBinding
import com.georgian.farmington.databinding.ActivityProductBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class ProductActivity : AppCompatActivity() {
    lateinit var bottomNav : BottomNavigationView
    val storageRef = Firebase.storage.reference
    private lateinit var activityProductBinding: ActivityProductBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        activityProductBinding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(activityProductBinding.root)

        val product = intent.getParcelableExtra("product") as? Product
        this.supportActionBar?.title = "Farmington - " + product?.product_name
        findViewById<TextView>(R.id.textView3).text = product?.product_name + "  " + product?.price + " $/Kg"
        findViewById<TextView>(R.id.textView2).text = product?.description
        findViewById<TextView>(R.id.textView4).text = "Quantity    " + product?.quantity + " Ton"

        val pathReference = storageRef.child("product_images/" + product?.image)

        val localFile = File.createTempFile(product?.image, "png")
        pathReference.getFile(localFile).addOnSuccessListener {
            activityProductBinding.imageView2.setImageBitmap(BitmapFactory.decodeFile(localFile.absolutePath))
        }.addOnFailureListener{
            activityProductBinding.imageView2.setImageResource(R.drawable.ricepic)
        }
    }
}