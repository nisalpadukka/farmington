package com.example.farmington

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import kotlin.concurrent.thread

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var pref=getSharedPreferences("pref", Context.MODE_PRIVATE)
        var user=pref.getString("name","Not Login")

        thread(true)
        {

            var img=findViewById<ImageView>(R.id.icon)
            var animation: Animation = AnimationUtils.loadAnimation(this,R.anim.fade)
            img.startAnimation(animation)


            Thread.sleep(5000)
            if(user.equals("Not Login")) {
                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}