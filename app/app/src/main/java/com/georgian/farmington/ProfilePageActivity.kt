package com.georgian.farmington

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class ProfilePageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)
        val storageRef = Firebase.storage.reference

// Create a reference with an initial file path and name
        val pathReference = storageRef.child("image/imgg.png")

        val localFile = File.createTempFile("imgg", "png")

        pathReference.getFile(localFile).addOnSuccessListener {
            // Local temp file has been created
            val profileImgImageView : ImageView = findViewById(R.id.profileImg)
            profileImgImageView.setImageBitmap(BitmapFactory.decodeFile(localFile.absolutePath))

        }.addOnFailureListener {
            // Handle any errors
        }
    }
}