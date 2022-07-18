package com.georgian.farmington

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.*

class MarketplaceActivity : AppCompatActivity(), MarketPlaceRecyclerViewAdapter.OnArticleListner {

    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: MarketPlaceRecyclerViewAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var articleArrayList : ArrayList<Article>
    lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.supportActionBar?.title = "Farmington - Agri News"
        setContentView(R.layout.activity_marketplace)
        layoutManager = LinearLayoutManager(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewMarketplace)
        recyclerView.layoutManager = layoutManager;

        articleArrayList = arrayListOf()

        adapter = MarketPlaceRecyclerViewAdapter(articleArrayList, this)
        recyclerView.adapter = adapter;

        fetchMarketPlaceActivity();

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
                R.id.profile-> {
                    val intent = Intent(this,ProfilePageActivity::class.java)
                    startActivity(intent)

                }
            }
        }

    }

    private fun fetchMarketPlaceActivity(){
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
    override fun onArticleClick(article: Article) {
        val intent = Intent(this, AgriNewsActivity::class.java)
        intent.putExtra("article", article)
        startActivity(intent)

    }
}