package com.georgian.farmington

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class HomeActivity : AppCompatActivity() {

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        // Create a storage reference from our app
        val storageRef = Firebase.storage.reference

// Create a reference with an initial file path and name
        val pathReference = storageRef.child("image/imgg.png")

        val localFile = File.createTempFile("imgg", "png")

         this.supportActionBar?.title = "Farmington - Home"

         pathReference.getFile(localFile).addOnSuccessListener {
             // Local temp file has been created
             val profileImgImageView : ImageButton = findViewById(R.id.profileButton)
             profileImgImageView.setImageBitmap(BitmapFactory.decodeFile(localFile.absolutePath))

         }.addOnFailureListener {
             // Handle any errors
         }

        val agriNewsButtonVar: ImageButton = findViewById (R.id.agrinewsButton)
        agriNewsButtonVar.setOnClickListener()
        {
            val intent = Intent(this, AgriNewsHomeActivity::class.java)
            startActivity(intent)
        }

        val weatherButtonVar: ImageButton = findViewById (R.id.weatherButton)
        weatherButtonVar.setOnClickListener()
        {
            val intent = Intent(this, WeatherActivity::class.java)
            startActivity(intent)
        }

        val profileButtonVar: ImageButton = findViewById (R.id.profileButton)
        profileButtonVar.setOnClickListener()
        {
            val intent = Intent(this, ProfilePageActivity::class.java)
            startActivity(intent)
        }

    }
    override fun onBackPressed() {
        finish()
    }
}