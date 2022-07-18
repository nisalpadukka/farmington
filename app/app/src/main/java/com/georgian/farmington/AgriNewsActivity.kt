package com.georgian.farmington


import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.georgian.farmington.databinding.ActivityNewsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File


class AgriNewsActivity : AppCompatActivity(){

    private lateinit var activityNewsBinding: ActivityNewsBinding
    val storageRef = Firebase.storage.reference
    lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {

        //navigation
        bottomNav = findViewById(R.id.bottom_navigation) as BottomNavigationView
        bottomNav.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.news -> {
                    val intent = Intent(this, AgriNewsHomeActivity::class.java)
                    startActivity(intent)
                }
                R.id.marketplace -> {
                    val intent = Intent(this,MarketplaceActivity::class.java)
                    startActivity(intent)

                }
                R.id.weather -> {
                    val intent = Intent(this,WeatherActivity::class.java)
                    startActivity(intent)

                }
                R.id.home-> {
                    val intent = Intent(this,HomeActivity::class.java)
                    startActivity(intent)

                }
            }
        }

        super.onCreate(savedInstanceState)

        activityNewsBinding = ActivityNewsBinding.inflate(layoutInflater)

        val article = intent.getParcelableExtra("article") as? Article
        activityNewsBinding.toolbarLayout.title = article?.title
        activityNewsBinding.mainPara.text = ""
        activityNewsBinding.contentBox.text = article?.description

        activityNewsBinding.toolbarLayout.setBackgroundResource(R.drawable.loading)
        val pathReference = storageRef.child("article_images/" + article?.image)

        val localFile = File.createTempFile(article?.image, "png")
        pathReference.getFile(localFile).addOnSuccessListener {
            val drawable = BitmapDrawable(getResources(), BitmapFactory.decodeFile(localFile.absolutePath))
            activityNewsBinding.toolbarLayout.background = drawable
        }.addOnFailureListener{
            activityNewsBinding.toolbarLayout.setBackgroundResource(R.drawable.ricepic)
        }

        setSupportActionBar(findViewById(R.id.toolbar))
        setContentView(activityNewsBinding.root)
    }

}
