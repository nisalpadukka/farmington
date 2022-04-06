package com.georgian.farmington

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Path
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.lang.Float.min
import kotlin.math.min

class ProfilePageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val user = Firebase.auth.currentUser
        if (user != null) {
            Log.d("Check","User is signed in")
        } else {
            Log.d("Check"," No User is signed in")
            Toast.makeText(this, "Please Sign In again", Toast.LENGTH_SHORT)
                .show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)

        this.supportActionBar?.title = "Farmington - Profile"
        val storageRef = Firebase.storage.reference


        // Create a reference with an initial file path and name
        val pathReference = storageRef.child("image/"+ user?.uid.toString())

        val localFile = File.createTempFile(user?.uid.toString(), "png")
        val profileImgImageView : ImageView = findViewById(R.id.profileImg)
        pathReference.getFile(localFile).addOnSuccessListener {
            // Local temp file has been created
            val bitmap: Bitmap? = BitmapFactory.decodeFile(localFile.absolutePath)

            bitmap?.cropCircularArea(700)?.apply {
                profileImgImageView.setImageBitmap(this)
            }

        }.addOnFailureListener {
            // Handle any errors

            profileImgImageView.setImageDrawable(getResources().getDrawable(R.drawable.default_profile))

        }
        val displayNameText : TextView = findViewById(R.id.userName)
        displayNameText.text = Firebase.auth.currentUser?.displayName.toString()


        //ACCOUNT INFORMATION
        val accInfoButton: ImageButton = findViewById (R.id.accInfoArrow)
        accInfoButton.setOnClickListener()
        {
            val intent = Intent(this, AccountInformation::class.java)
            startActivity(intent)
        }


        //SIGN OUT FUNCTIONALITY

        val signOutButton: ImageButton = findViewById (R.id.signOutArrow)
        signOutButton.setOnClickListener()
        {
            Firebase.auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }




    }

    fun Bitmap.cropCircularArea(
        diameter:Int = min(width,height)
    ): Bitmap?{
        val bitmap = Bitmap.createBitmap(
            width, // width in pixels
            height, // height in pixels
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)

        // create a circular path
        val path = Path()
        val length = min(diameter, min(width,height))
        val radius = length / 2F // in pixels
        path.apply {
            addCircle(
                width/2f,
                height/2f,
                radius,
                Path.Direction.CCW
            )
        }

        // draw circular bitmap on canvas
        canvas.clipPath(path)
        canvas.drawBitmap(this,0f,0f,null)

        val x = (width - length)/2
        val y = (height - length)/2

        // return cropped circular bitmap
        return Bitmap.createBitmap(
            bitmap, // source bitmap
            x, // x coordinate of the first pixel in source
            y, // y coordinate of the first pixel in source
            length, // width
            length // height
        )
    }

}