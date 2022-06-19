package com.georgian.farmington

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Path
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.ktx.storage
import java.io.File
import kotlin.math.min

class HomeActivity : AppCompatActivity() {

    lateinit var bottomNav : BottomNavigationView

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



         this.supportActionBar?.title = "Farmington - Profile"
        setContentView(R.layout.activity_home)

        //navigation
         bottomNav = findViewById(R.id.bottom_navigation) as BottomNavigationView

         bottomNav.setOnNavigationItemReselectedListener {
             when (it.itemId) {
                 R.id.home -> {


                 }
                 R.id.news -> {
                     val intent = Intent(this, AgriNewsHomeActivity::class.java)
                     startActivity(intent)
                 }
                 R.id.marketplace -> {

                 }
             }
         }

        // Create a storage reference from our app

         val storageRef = Firebase.storage.reference
         val user = Firebase.auth.currentUser

         // Create a reference with an initial file path and name
         val pathReference = storageRef.child("image/"+ user?.uid.toString())

         val localFile = File.createTempFile(user?.uid.toString(), "png")
         val profileImgImageView : ImageButton = findViewById(R.id.profileButton)
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

         val marketPlaceButoon: ImageButton = findViewById (R.id.marketplaceButton)
         marketPlaceButoon.setOnClickListener()
         {
             val intent = Intent(this, MarketplaceActivity::class.java)
             startActivity(intent)
         }

         FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
             if (!task.isSuccessful) {
                 Log.i("PushN", "Fetching FCM registration token failed", task.exception)
                 return@OnCompleteListener
             }

             // Get new FCM registration token
             val token = task.result
             Log.i("PushN", "Token " + token)

         })

//         ONE TIME LOGIN LOGIC
         var pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
         var str = pref.getString("email","null")

    }
    override fun onBackPressed() {
        finish()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show()
//        var intent = Intent(this, LoginActivity::class.java)
//        startActivity(intent)
//        finish()
//        return super.onOptionsItemSelected(item)
        when(item!!.itemId)
        {
            R.id.item1->
            {
                var pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
                var edit = pref.edit()
                edit.clear()
                edit.commit()

                var intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()

            }
        }
        return super.onOptionsItemSelected(item)
    }
}