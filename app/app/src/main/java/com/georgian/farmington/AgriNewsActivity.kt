package com.georgian.farmington


import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.georgian.farmington.databinding.ActivityNewsBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File


class AgriNewsActivity : AppCompatActivity(){

    private lateinit var activityNewsBinding: ActivityNewsBinding
    val storageRef = Firebase.storage.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityNewsBinding = ActivityNewsBinding.inflate(layoutInflater)

        val article = intent.getParcelableExtra("article") as? Article
        activityNewsBinding.toolbarLayout.title = article?.title
        activityNewsBinding.mainPara.text = ""
        activityNewsBinding.contentBox.text = article?.description

        activityNewsBinding.toolbarLayout.setBackgroundResource(R.drawable.loading)
        val pathReference = storageRef.child("article_images/" + article?.image)

        pathReference.downloadUrl
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
