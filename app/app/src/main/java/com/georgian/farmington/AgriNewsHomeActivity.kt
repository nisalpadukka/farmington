package com.georgian.farmington

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*

class AgriNewsHomeActivity : AppCompatActivity(), AgriNewsRecyclerViewAdapter.OnArticleListner {

    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: AgriNewsRecyclerViewAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var articleArrayList : ArrayList<Article>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agri_news_home)
        layoutManager = LinearLayoutManager(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager;

        articleArrayList = arrayListOf()

        adapter = AgriNewsRecyclerViewAdapter(articleArrayList, this)
        recyclerView.adapter = adapter;

        fetchNewsArticles();
    }

    private fun fetchNewsArticles(){
        db = FirebaseFirestore.getInstance();
        db.collection("Articles").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                if (error != null) {
                    Log.e("Firebase", error.toString())
                    return
                }

                for (article : DocumentChange in value?.documentChanges!!){

                    if (article.type == DocumentChange.Type.ADDED){
                        articleArrayList.add(article.document.toObject(Article::class.java))
                    }

                }

                adapter.notifyDataSetChanged()
            }

        })

    }
    override fun onArticleClick() {
        val intent = Intent(this, AgriNewsActivity::class.java)
        startActivity(intent)
    }
}