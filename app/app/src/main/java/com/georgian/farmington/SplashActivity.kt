package com.georgian.farmington

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import com.georgian.farmington.databinding.ActivitySplashBinding
import com.google.firebase.auth.FirebaseAuth
import kotlin.concurrent.thread

class SplashActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashBinding
    private lateinit var user: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var img=findViewById<ImageView>(R.id.icon1)
        var animation: Animation = AnimationUtils.loadAnimation(this,R.anim.fade)
        img.startAnimation(animation)

        thread(true)
        {

            Thread.sleep(5000)
            var pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
            var user = pref.getString("email", "null")
            if(user.equals("null")) {

                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                var intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
    }

}