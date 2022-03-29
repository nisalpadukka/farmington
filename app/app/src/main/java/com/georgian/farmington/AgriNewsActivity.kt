package com.georgian.farmington

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.georgian.farmington.databinding.ActivityNewsBinding
import com.google.firebase.firestore.*

class AgriNewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
    }
}