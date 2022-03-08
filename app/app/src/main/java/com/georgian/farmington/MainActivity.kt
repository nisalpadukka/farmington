package com.georgian.farmington

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val agriNewsButtonVar: Button = findViewById (R.id.agriNewsButton)
        agriNewsButtonVar.setOnClickListener()
        {
            val intent = Intent(this, AgriNewsActivity::class.java)
            startActivity(intent)
        }
    }
}