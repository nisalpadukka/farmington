package com.georgian.farmington

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MarketplaceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketplace2)


        val buyButtonVar: Button = findViewById(R.id.BuyButton)
        buyButtonVar.setOnClickListener()
        {
            val intent = Intent(this, marketplacebuyActivity::class.java)
            startActivity(intent)
        }

        val sellButtonVar: Button = findViewById(R.id.Sell)
        sellButtonVar.setOnClickListener()
        {
            val intent = Intent(this, MarketplacesellActivity::class.java)
            startActivity(intent)
        }

    }
}
