package com.georgian.farmington

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class MarketplaceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketplace)

        val WheatButtonVar: ImageButton = findViewById (R.id.wheatimage)
        WheatButtonVar.setOnClickListener()
        {
            val intent = Intent(this, ProductActivity::class.java)
            startActivity(intent)
        }

    }
}