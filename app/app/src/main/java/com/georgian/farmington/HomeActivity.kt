package com.georgian.farmington

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        val agriNewsButtonVar: Button = findViewById (R.id.agrinewsButton)
        agriNewsButtonVar.setOnClickListener()
        {
            val intent = Intent(this, AgriNewsHomeActivity::class.java)
            startActivity(intent)
        }

        val weatherButtonVar: Button = findViewById (R.id.weatherButton)
        weatherButtonVar.setOnClickListener()
        {
            val intent = Intent(this, WeatherActivity::class.java)
            startActivity(intent)
        }

    }
}